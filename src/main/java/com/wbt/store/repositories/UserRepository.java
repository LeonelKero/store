package com.wbt.store.repositories;

import com.wbt.store.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"tags"})
    Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {"addresses"})
    // must add a query here
//    List<User> findAllWithAddresses();
}
