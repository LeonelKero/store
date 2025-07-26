package com.wbt.store.repository.impl;

import com.wbt.store.entity.User;
import com.wbt.store.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Map<String, User> users = new HashMap<>();

    @Override
    public void save(final User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
//        users.getOrDefault(email, null);
        return Optional.of(users.get(email));
    }
}
