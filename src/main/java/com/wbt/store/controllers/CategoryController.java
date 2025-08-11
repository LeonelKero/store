package com.wbt.store.controllers;

import com.wbt.store.dtos.CategoryRequest;
import com.wbt.store.dtos.CategoryResponse;
import com.wbt.store.entities.Category;
import com.wbt.store.mappers.CategoryMapper;
import com.wbt.store.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/api/v1/categories"})
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @PostMapping
    public ResponseEntity<?> createCategory(final @Valid @RequestBody CategoryRequest request, final UriComponentsBuilder uriBuilder) {
        if (repository.findByNameIgnoreCase(request.name().trim()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Category name already exists"));
        final var newCategory = new Category();
        newCategory.setName(request.name().trim().toUpperCase());
        final var savedCategory = this.repository.save(newCategory);
        final var uri = uriBuilder.path("/api/v1/categories/{id}").buildAndExpand(savedCategory.getId()).toUri();
        return ResponseEntity.created(uri).body(this.mapper.toCategoryResp(savedCategory));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> allCategories() {
        return ResponseEntity.ok(this.repository.findAll().stream().map(this.mapper::toCategoryResp).toList());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<CategoryResponse> getCategory(final @PathVariable(name = "id") Byte id) {
        return this.repository.findById(id)
                .map(category -> ResponseEntity.ok(this.mapper.toCategoryResp(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<CategoryResponse> removeCategory(final @PathVariable(name = "id") Byte id) {
        return this.repository.findById(id).map(category -> {
            this.repository.delete(category);
            return ResponseEntity.ok(this.mapper.toCategoryResp(category));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> update(final @PathVariable(name = "id") Byte id, final @Valid @RequestBody CategoryRequest request) {
        return this.repository.findById(id).map(category -> {
            category.setName(request.name().trim().toUpperCase());
            final var updatedCategory = this.repository.save(category);
            return ResponseEntity.ok(this.mapper.toCategoryResp(updatedCategory));
        }).orElse(ResponseEntity.notFound().build());
    }
}
