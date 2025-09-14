package com.wbt.store.controllers;

import com.wbt.store.dtos.AddToCartRequest;
import com.wbt.store.dtos.CartDto;
import com.wbt.store.dtos.ItemRequestDto;
import com.wbt.store.entities.Cart;
import com.wbt.store.filters.CartFilter;
import com.wbt.store.mappers.CartMapper;
import com.wbt.store.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final CartMapper cartMapper;
    private final CartService service;

    @GetMapping
    public ResponseEntity<Page<CartDto>> allCarts(final @ModelAttribute CartFilter filter) {
        final var carts = this.service.getAll(filter).map(this.cartMapper::toCartDto);
        return ResponseEntity.ok(carts);
    }

    @PostMapping
    public ResponseEntity<CartDto> initCart(final UriComponentsBuilder uriBuilder) {
        Cart saved = this.service.save(new Cart());
        final var uri = uriBuilder
                .path("/api/v1/carts/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        final var cart = new CartDto(saved.getId(), saved.getCreatedDate(), Collections.emptySet(), BigDecimal.ZERO);
        return ResponseEntity.created(uri).body(cart);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CartDto> getCart(final @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(this.cartMapper.toCartDto(this.service.get(id)));
    }

    // Add item to the cart
    @PostMapping(path = {"/{id}"})
    public ResponseEntity<CartDto> addProduct(final @PathVariable(name = "id") UUID id, final @Valid @RequestBody AddToCartRequest request) {
        final var updatedCart = this.service.add(id, request);
        CartDto cartDto = this.cartMapper.toCartDto(updatedCart);
        return ResponseEntity.ok(cartDto);
    }

    // Remove item from the cart
    @DeleteMapping(path = {"/{id}/items"})
    public ResponseEntity<CartDto> clearCart(final @PathVariable(name = "id") UUID id) {
        final var clearedCart = this.service.clear(id);
        return ResponseEntity.ok(this.cartMapper.toCartDto(clearedCart));
    }

    // Update cart content
    @PutMapping(path = {"/{id}/items/{productId}"})
    public ResponseEntity<CartDto> update(final @PathVariable(name = "id") UUID id, final @PathVariable(name = "productId") Long productId, final @Valid @RequestBody ItemRequestDto itemDto) {
        final var updatedCart = this.service.update(id, productId, itemDto);
        return ResponseEntity.ok(this.cartMapper.toCartDto(updatedCart));
    }

    // Remove product from the cart
    @DeleteMapping(path = {"/{id}/items/{productId}"})
    public ResponseEntity<CartDto> removeProductItem(final @PathVariable(name = "id") UUID id, final @PathVariable(name = "productId") Long productId) {
        final var updatedCart = this.service.remove(id, productId);
        if (updatedCart == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(this.cartMapper.toCartDto(updatedCart));
    }

    // Delete the cart
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Void> deleteCart(final @PathVariable(name = "id") UUID id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
