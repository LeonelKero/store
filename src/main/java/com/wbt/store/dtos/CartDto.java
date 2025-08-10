package com.wbt.store.dtos;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CartDto(
        UUID id,
        Set<CartItemDto> items,
        BigDecimal price
) {
}
