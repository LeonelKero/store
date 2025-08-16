package com.wbt.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank(message = "Product name is required")
        @Size(min = 2, max = 20, message = "Product name must be at least 2-20 characters")
        String name,

        @NotBlank(message = "Product description is required")
        @Size(min = 8, max = 256, message = "Product description must be between 8-256 characters")
        String description,

        @NotNull(message = "Product price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Category ID is required")
        Byte categoryId
) {
}
