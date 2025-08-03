package com.wbt.store.dtos;

public record UserRequestDto(
        String name,
        String email,
        String password
) {
}
