package com.wbt.store.services;

import com.wbt.store.dtos.AddToCartRequest;
import com.wbt.store.dtos.ItemRequestDto;
import com.wbt.store.entities.Cart;
import com.wbt.store.filters.CartFilter;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CartService {
    Page<Cart> getAll(CartFilter filter);

    Cart save(Cart cart);

    Cart get(UUID id);

    Cart add(UUID cartId, AddToCartRequest request);

    Cart clear(UUID id);

    Cart update(UUID id, Long productId, ItemRequestDto itemDto);

    Cart remove(UUID id, Long productId);

    void delete(UUID id);
}
