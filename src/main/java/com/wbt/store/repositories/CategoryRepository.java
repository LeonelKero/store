package com.wbt.store.repositories;

import com.wbt.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Byte> {

    Optional<Category> findByNameIgnoreCase(String name);
}
