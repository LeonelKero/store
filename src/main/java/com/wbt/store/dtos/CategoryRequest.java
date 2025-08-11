package com.wbt.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        @Min(value = 2, message = "At least 02 characters for name required")
        @Max(value = 20, message = "Maximum of 20 characters for category name")
        String name
) {
}
