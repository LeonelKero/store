package com.wbt.store.services.impl;

import com.wbt.store.dtos.AddToCartRequest;
import com.wbt.store.dtos.ItemRequestDto;
import com.wbt.store.entities.Cart;
import com.wbt.store.entities.CartItem;
import com.wbt.store.exceptions.EntityResourceNotFoundException;
import com.wbt.store.filters.CartFilter;
import com.wbt.store.repositories.CartRepository;
import com.wbt.store.services.CartService;
import com.wbt.store.services.ProductService;
import com.wbt.store.specifications.CartSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final ProductService productService;

    @Override
    public Page<Cart> getAll(final CartFilter filter) {
        final var pr = PageRequest.of(filter.getPage(), filter.getSize());
        return this.repository.findAll(getSpecs(filter), pr);
    }

    @Override
    public Cart save(final Cart cart) {
        return this.repository.save(cart);
    }

    @Override
    public Cart get(final UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityResourceNotFoundException("Cart element not found with ID: " + id));
    }

    @Override
    public Cart add(UUID cartId, AddToCartRequest request) {
        final var cart = get(cartId);
        final var product = this.productService.getProduct(request.productId());

        final var cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.productId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            // item already in cart
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            // first add of the item
            final var newItem = CartItem.builder()
                    .quantity(1)
                    .product(product)
                    .build();
            cart.addItem(newItem);
        }

        return save(cart);
    }

    @Override
    public Cart clear(final UUID id) {
        final var cart = get(id);
        cart.clear();
        return this.repository.save(cart);
    }

    @Override
    public Cart update(final UUID id, final Long productId, final ItemRequestDto itemDto) {
        final var cart = get(id);
        final var optionalItem = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst();

        if (optionalItem.isEmpty())
            throw new EntityResourceNotFoundException("Product not found with ID: " + productId);

        final var it = optionalItem.get();
        it.setQuantity(itemDto.quantity());

        return this.repository.save(cart);
    }

    @Override
    public Cart remove(final UUID id, final Long productId) {
        final var cart = get(id);
        final var item = cart.getItems().stream().filter(it -> it.getProduct().getId().equals(productId)).findFirst();

        if (item.isEmpty()) return null;

        cart.removeItem(item.get());
        return this.repository.save(cart);
    }

    @Override
    public void delete(UUID id) {
        final var cart = get(id);
        this.repository.delete(cart);
    }

    private Specification<Cart> getSpecs(final CartFilter filter) {
        return Specification.where(
                CartSpecification.hasProductWithName(filter.getProductName())
                        .and(CartSpecification.hasDate(filter.getCreatedAt()))
                        .and(CartSpecification.hasDate(filter.getCreatedAt(), filter.getGap()))
        );
    }
}
