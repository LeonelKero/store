package com.wbt.store.repositories.impl;

import com.wbt.store.entities.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepository {
    private static final Map<String, User> users = new HashMap<>();

    public void save(final User user) {
        users.put(user.getEmail(), user);
    }

    public Optional<User> findByEmail(final String email) {
//        users.getOrDefault(email, null);
        return Optional.of(users.get(email));
    }
}
