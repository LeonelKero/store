package com.wbt.store.dtos;

import java.math.BigDecimal;

public record ProductItemDto(
        Long id,
        String name,
        BigDecimal price
) {
}
