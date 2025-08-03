package com.wbt.store.dtos;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Byte categoryId
) {
}
