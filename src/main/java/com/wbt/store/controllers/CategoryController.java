package com.wbt.store.controllers;

import com.wbt.store.dtos.CategoryRequest;
import com.wbt.store.dtos.CategoryResponse;
import com.wbt.store.mappers.CategoryMapper;
import com.wbt.store.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/categories"})
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping
    public ResponseEntity<?> createCategory(final @Valid @RequestBody CategoryRequest request, final UriComponentsBuilder uriBuilder) {
        final var savedCategory = this.service.create(request);
        final var uri = uriBuilder.path("/api/v1/categories/{id}").buildAndExpand(savedCategory.getId()).toUri();
        return ResponseEntity.created(uri).body(this.mapper.toCategoryResp(savedCategory));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> allCategories() {
        return ResponseEntity.ok(this.service.getAll().stream().map(this.mapper::toCategoryResp).toList());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<CategoryResponse> getCategory(final @PathVariable(name = "id") Byte id) {
        final var category = this.service.get(id);
        return ResponseEntity.ok(this.mapper.toCategoryResp(category));
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Byte> removeCategory(final @PathVariable(name = "id") Byte id) {
        return ResponseEntity.ok().body(this.service.delete(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> update(final @PathVariable(name = "id") Byte id, final @Valid @RequestBody CategoryRequest request) {
        final var updatedCategory = this.service.update(id, request);
        return ResponseEntity.ok(this.mapper.toCategoryResp(updatedCategory));
    }
}
