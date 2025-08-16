package com.wbt.store.controllers;

import com.wbt.store.dtos.ProductDto;
import com.wbt.store.dtos.ProductRequestDto;
import com.wbt.store.entities.Category;
import com.wbt.store.entities.Product;
import com.wbt.store.mappers.CategoryMapper;
import com.wbt.store.repositories.CategoryRepository;
import com.wbt.store.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/products"})
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(final @RequestParam(name = "categoryId", required = false) Byte category) {
        if (category == null)
            return ResponseEntity.ok(this.productRepository.findByProductsWithCategory().stream().map(this::getProductDto).toList());

        final var products = productRepository.findByCategory_IdOrderByPriceAsc(category);
        return ResponseEntity.ok(products.stream().map(this::getProductDto).toList());
    }

    @GetMapping(path = {"/category/{id}"})
    public ResponseEntity<List<ProductDto>> getProductsInCategory(final @PathVariable(name = "id") Byte id) {
        return this.categoryRepository.findById(id)
                .map(category -> {
                    final var products = this.productRepository.findByCategory_Id(category.getId()).stream().map(this::getProductDto).toList();
                    return ResponseEntity.ok(products);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<ProductDto> getProductById(final @PathVariable Long id) {
        return this.productRepository.findById(id)
                .map(product -> ResponseEntity.ok(getProductDto(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<ProductDto> create(final @Valid @RequestBody ProductRequestDto requestDto, final UriComponentsBuilder uriBuilder) {
        final var category = getCategory(requestDto.categoryId());
        final var product = Product.builder()
                .price(requestDto.price())
                .name(requestDto.name())
                .description(requestDto.description())
                .category(category)
                .build();
        final var saved = this.productRepository.save(product);
        return ResponseEntity
                .created(uriBuilder.path("/api/v1/products/{id}").buildAndExpand(saved.getId()).toUri())
                .body(getProductDto(saved));
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Void> delete(final @PathVariable(name = "id") Long id) {
        return this.productRepository.findById(id)
                .map(product -> {
                    this.productRepository.delete(product);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<ProductDto> update(final @PathVariable(name = "id") Long id, final @RequestBody ProductRequestDto requestDto) {
        return this.productRepository.findById(id)
                .map(product -> {
                    if (requestDto.name() != null) product.setName(requestDto.name());
                    if (requestDto.description() != null) product.setDescription(requestDto.description());
                    if (requestDto.price() != null) product.setPrice(requestDto.price());
                    if (requestDto.categoryId() != null) {
                        final var category = getCategory(requestDto.categoryId());
                        product.setCategory(category);
                    }
                    final var updated = this.productRepository.save(product);
                    return ResponseEntity.ok().body(getProductDto(updated));
                }).orElse(ResponseEntity.notFound().build());
    }

    private Category getCategory(Byte categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("No category found with id: " + categoryId));
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
