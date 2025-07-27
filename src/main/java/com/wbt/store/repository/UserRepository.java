package com.wbt.store.repository;

import com.wbt.store.entities.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}
