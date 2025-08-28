package com.wbt.store.services;

import com.wbt.store.dtos.CategoryRequest;
import com.wbt.store.entities.Category;
import com.wbt.store.exceptions.EntityResourceNotFoundException;
import com.wbt.store.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category create(@Valid CategoryRequest request) {
        if (repository.findByNameIgnoreCase(request.name().trim()).isPresent())
            throw new EntityResourceNotFoundException("Category resource not found");

        final var newCategory = new Category();
        newCategory.setName(request.name().trim().toUpperCase());

        return this.repository.save(newCategory);
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Optional<Category> get(Byte id) {
        return this.repository.findById(id);
    }
}
