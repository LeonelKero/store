package com.wbt.store.dtos;

public record UserUpdateRequest(
        String name,
        String email
) {
}
