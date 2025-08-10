package com.wbt.store.controllers;

import com.wbt.store.dtos.AddToCartRequest;
import com.wbt.store.dtos.CartDto;
import com.wbt.store.entities.Cart;
import com.wbt.store.entities.CartItem;
import com.wbt.store.mappers.CartMapper;
import com.wbt.store.repositories.CartRepository;
import com.wbt.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
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
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> removeItem(final @PathVariable(name = "id") UUID id, final @RequestParam(name = "item") Long itemId) {
        return this.repository.findById(id).map(cart -> {
            final var optionalItem = cart.getItems().stream().filter(item -> item.getId().equals(itemId)).findFirst();
            if (optionalItem.isEmpty()) return ResponseEntity.notFound().build();
            cart.removeItem(optionalItem.get());
            this.repository.save(cart);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Update cart content
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<?> update(final @PathVariable(name = "id") UUID id) {
        // How to update (is it add or remove item from the cart?)
        return null;
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
