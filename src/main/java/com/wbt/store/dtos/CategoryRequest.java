package com.wbt.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(min = 2, max = 20, message = "Name must be between 2-20 characters")
        String name
) {
}
