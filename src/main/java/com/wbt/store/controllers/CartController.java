package com.wbt.store.controllers;

import com.wbt.store.dtos.AddToCartRequest;
import com.wbt.store.dtos.CartDto;
import com.wbt.store.dtos.ItemRequestDto;
import com.wbt.store.entities.Cart;
import com.wbt.store.entities.CartItem;
import com.wbt.store.mappers.CartMapper;
import com.wbt.store.repositories.CartRepository;
import com.wbt.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = {"/api/v1/carts"})
@RequiredArgsConstructor
public class CartController {
    private final CartRepository repository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    // Show cart content
    @PostMapping
    public ResponseEntity<CartDto> initCart(final UriComponentsBuilder uriBuilder) {
        Cart saved = this.repository.save(new Cart());
        final var uri = uriBuilder
                .path("/api/v1/carts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        final var cart = new CartDto(saved.getId(), Collections.emptySet(), BigDecimal.ZERO);
        return ResponseEntity.created(uri).body(cart);
    }

    // Get a cart by ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<CartDto> getCart(final @PathVariable(name = "id") UUID id) {
        return this.repository.findById(id).map(cart -> {
                    final var foundCart = this.cartMapper.toCartDto(cart);
                    return ResponseEntity.ok().body(foundCart);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Add item to the cart
    @PostMapping(path = {"/{id}"})
    public ResponseEntity<CartDto> addProduct(final @PathVariable(name = "id") UUID id, final @Valid @RequestBody AddToCartRequest request) {
        final var cart = this.repository.findById(id);
        if (cart.isEmpty()) return ResponseEntity.notFound().build();
        final var product = this.productRepository.findById(request.productId());
        if (product.isEmpty()) return ResponseEntity.badRequest().build();

        Cart existingCart = cart.get();
        final var cartItem = existingCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.productId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            final var newItem = CartItem.builder()
                    .quantity(1)
                    .cart(existingCart)
                    .product(product.get())
                    .build();
            existingCart.addItem(newItem);
        }

        return ResponseEntity.ok(this.cartMapper.toCartDto(this.repository.save(existingCart)));
    }

    // Remove item from the cart
    @DeleteMapping(path = {"/{id}/items"})
    public ResponseEntity<?> clearCart(final @PathVariable(name = "id") UUID id, final @RequestParam(name = "item") Long itemId) {
        return this.repository.findById(id).map(cart -> {
            cart.getItems().forEach(item -> {
                cart.removeItem(item);
                this.repository.save(cart);
            });

            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Cart not found")));
    }

    // Update cart content
    @PutMapping(path = {"/{id}/items/{productId}"})
    public ResponseEntity<?> update(final @PathVariable(name = "id") UUID id, final @PathVariable(name = "productId") Long productId, final @Valid @RequestBody ItemRequestDto itemDto) {
        return this.repository.findById(id).map(cart -> {
            final var optionalItem = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst();
            if (optionalItem.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Unable to cart item with ID: " + productId)
            );

            final var it = optionalItem.get();
            it.setQuantity(itemDto.quantity());

            return ResponseEntity.ok(this.cartMapper.toCartDto(this.repository.save(cart)));

        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("message", "Unable to find cart with ID: " + id)));
    }

    // Remove product from the cart
    @DeleteMapping(path = {"/{id}/items/{productId}"})
    public ResponseEntity<?> removeProductItem(final @PathVariable(name = "id") UUID id, final @PathVariable(name = "productId") Long productId) {
        return this.repository.findById(id).map(cart -> {
            final var item = cart.getItems().stream().filter(it -> it.getProduct().getId().equals(productId)).findFirst();
            if (item.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found in the cart."));
            cart.removeItem(item.get());
            this.repository.save(cart);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Cart not found with ID: " + id)));
    }

    // Delete the cart
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteCart(final @PathVariable(name = "id") UUID id) {
        return this.repository.findById(id).map(cart -> {
            this.repository.delete(cart);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
