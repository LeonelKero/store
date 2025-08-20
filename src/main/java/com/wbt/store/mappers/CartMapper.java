package com.wbt.store.mappers;

import com.wbt.store.dtos.CartDto;
import com.wbt.store.dtos.CartItemDto;
import com.wbt.store.dtos.ProductItemDto;
import com.wbt.store.entities.Cart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDto toCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getCreatedDate(),
                cart.getItems().stream().map(item -> new CartItemDto(
                        new ProductItemDto(item.getProduct().getId(), item.getProduct().getName(), item.getProduct().getPrice()),
                        item.getQuantity(),
                        item.getTotalPrice()
                )).collect(Collectors.toSet()),
                cart.calculatePrice());
    }

}
