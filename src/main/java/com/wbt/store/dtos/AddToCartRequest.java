package com.wbt.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddToCartRequest(
        @NotNull(message = "The product Id is required")
        @Positive(message = "Product ID must be a positive value")
        @Min(value = 1, message = "Id cannot be negative")
        Long productId
) {
}
