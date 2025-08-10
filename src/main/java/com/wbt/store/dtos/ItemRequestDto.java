package com.wbt.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDto(
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Min quantity is one (1)")
        @Max(value = 256, message = "Max quantity exceeded (256)")
        Integer quantity
) {
}
