package com.wbt.store.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddToCartRequest(
        @NotBlank(message = "Missing product Id")
        Long productId
) {
}
