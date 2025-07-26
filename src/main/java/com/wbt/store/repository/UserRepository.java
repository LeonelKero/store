package com.wbt.store.repository;

import com.wbt.store.entity.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}
