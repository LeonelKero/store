package com.wbt.store.dtos;

import com.wbt.store.annotations.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less then 255 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email")
        @Lowercase(message = "Email must be in lowercase")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 25, message = "Password must be between 6 to 25 characters")
        String password
) {
}
