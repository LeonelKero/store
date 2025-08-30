package com.wbt.store.services;

import com.wbt.store.dtos.CategoryRequest;
import com.wbt.store.entities.Category;
import com.wbt.store.exceptions.EntityResourceNotFoundException;
import com.wbt.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category create(final CategoryRequest request) {
        if (repository.findByNameIgnoreCase(request.name().trim()).isPresent())
            throw new EntityResourceNotFoundException("Category resource not found");

        final var newCategory = new Category();
        newCategory.setName(request.name().trim().toUpperCase());

        return this.repository.save(newCategory);
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Category get(final Byte id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityResourceNotFoundException("Category resource not found"));
    }

    public Byte delete(final Byte id) {
        return this.repository.findById(id).map(category -> {
            this.repository.delete(category);
            return category.getId();
        }).orElseThrow(() -> new EntityResourceNotFoundException("Category resource not found"));
    }

    public Category update(final Byte id, final CategoryRequest request) {
        return this.repository.findById(id).map(category -> {
            category.setName(request.name().trim().toUpperCase());
            return this.repository.save(category);
        }).orElseThrow(() -> new EntityResourceNotFoundException("Category resource not found"));
    }
}
