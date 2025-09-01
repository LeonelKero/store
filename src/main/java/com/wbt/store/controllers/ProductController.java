package com.wbt.store.controllers;

import com.wbt.store.dtos.ProductDto;
import com.wbt.store.dtos.ProductRequestDto;
import com.wbt.store.entities.Product;
import com.wbt.store.filters.ProductFilter;
import com.wbt.store.mappers.CategoryMapper;
import com.wbt.store.repositories.ProductRepository;
import com.wbt.store.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = {"/api/v1/products"})
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService service;
    private final CategoryMapper categoryMapper;

    @GetMapping(path = {"/all"})
    public ResponseEntity<Page<ProductDto>> allProducts(final @ModelAttribute ProductFilter productFilter) {
        return ResponseEntity.ok().body(this.service.filteredProducts(productFilter).map(this::getProductDto));
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<ProductDto> getProductById(final @PathVariable Long id) {
        final var product = this.service.getProduct(id);
        return ResponseEntity.ok(getProductDto(product));

    }

    @PostMapping
    public ResponseEntity<ProductDto> create(final @Valid @RequestBody ProductRequestDto requestDto, final UriComponentsBuilder uriBuilder) {
        final var saved = this.service.save(requestDto);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/products/{id}").buildAndExpand(saved.getId()).toUri())
                .body(getProductDto(saved));
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Void> delete(final @PathVariable(name = "id") Long id) {
        this.service.remove(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<ProductDto> update(final @PathVariable(name = "id") Long id, final @RequestBody ProductRequestDto requestDto) {
        final var product = this.service.update(id, requestDto);
        return ResponseEntity.ok(getProductDto(product));
    }

    private ProductDto getProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                categoryMapper.toCategoryResp(product.getCategory())
        );
    }

}
