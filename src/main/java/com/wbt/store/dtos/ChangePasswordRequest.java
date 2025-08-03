package com.wbt.store.dtos;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
