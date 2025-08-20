package com.wbt.store.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CartDto(
        UUID id,
        LocalDateTime createdAt,
        Set<CartItemDto> items,
        BigDecimal price
) {
}
