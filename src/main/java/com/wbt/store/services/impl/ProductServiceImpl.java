package com.wbt.store.services.impl;

import com.wbt.store.dtos.ProductRequestDto;
import com.wbt.store.entities.Product;
import com.wbt.store.exceptions.EntityResourceNotFoundException;
import com.wbt.store.filters.ProductFilter;
import com.wbt.store.repositories.CategoryRepository;
import com.wbt.store.repositories.ProductRepository;
import com.wbt.store.repositories.specifications.ProductSpec;
import com.wbt.store.services.ProductService;
import com.wbt.store.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        final var product = new Product();
        product.setName("keyword");

        final var matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "price")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        final var example = Example.of(product, matcher);

        return this.repository.findAll(example);
    }

    @Override
    public List<Product> fetchProductByCriteria() {
        return this.repository.findProductsByCriteria(null, BigDecimal.valueOf(1), BigDecimal.valueOf(10));
    }


    @Override
    public List<Product> findProductsBySpecification(final String name, final BigDecimal minPrice, final BigDecimal maxPrice) {
        Specification<Product> specification = Specification.where(null);
        if (name != null) specification.and(ProductSpec.hasName(name));
        if (minPrice != null) specification.and(ProductSpec.hasPriceEqualOrGreaterThen(minPrice));
        if (maxPrice != null) specification.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        return this.repository.findAll(specification);
    }

    @Override
    public List<Product> sortedProductByName() {
        Sort sort = Sort.by("name")
                .and(Sort.by("price").descending());
        return this.repository.findAll(sort);
    }

    @Override
    public Page<Product> fetchPagedProducts(final Integer page, final Integer size) {
        return this.repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Product> filteredProducts(final ProductFilter filter) {
        final var pageable = PageRequest.of(filter.getPage(), filter.getSize());
        final var productSpecs = buildProductSpecification(filter);
        return this.repository.findAll(productSpecs, pageable);
    }

    @Override
    public Product getProduct(final Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void remove(final Long id) {
        this.repository.findById(id)
                .map(product -> {
                    this.repository.delete(product);
                    return id;
                })
                .orElseThrow(() -> new EntityResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product update(final Long id, final ProductRequestDto request) {
        return this.repository.findById(id)
                .map(product -> {
                    if (request.name() != null) product.setName(request.name());
                    if (request.description() != null) product.setDescription(request.description());
                    if (request.price() != null) product.setPrice(request.price());
                    if (request.categoryId() != null) {
                        final var category = this.categoryRepository.findById(request.categoryId())
                                .orElseThrow(() -> new EntityResourceNotFoundException("Category not found with id: " + request.categoryId()));
                        product.setCategory(category);
                    }
                    return this.repository.save(product);
                }).orElseThrow(() -> new EntityResourceNotFoundException("Product not found with id: " + id));

    }

    @Override
    public Product save(final ProductRequestDto request) {
        final var category = this.categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityResourceNotFoundException("Category not found with id: " + request.categoryId()));
        final var product = Product.builder()
                .price(request.price())
                .name(request.name())
                .description(request.description())
                .category(category)
                .build();
        return this.repository.save(product);
    }

    private Specification<Product> buildProductSpecification(final ProductFilter filteringCriteria) {
        return Specification.where(
                ProductSpecification.hasName(filteringCriteria.getName())
                        .and(ProductSpecification.hasCategoryName(filteringCriteria.getCategoryName()))
                        .and(ProductSpecification.isInCategoryId(filteringCriteria.getCategoryId()))
                        .and(ProductSpecification.hasPriceLessThan(filteringCriteria.getPrice())));
    }
}
