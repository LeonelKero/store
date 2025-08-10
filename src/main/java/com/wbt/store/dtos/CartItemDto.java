package com.wbt.store.dtos;

import java.math.BigDecimal;

public record CartItemDto(
        ProductItemDto product,
        Integer quantity,
        BigDecimal totalPrice
) {
}
