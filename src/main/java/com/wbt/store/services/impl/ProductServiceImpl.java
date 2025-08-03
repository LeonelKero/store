package com.wbt.store.services.impl;

import com.wbt.store.entities.Product;
import com.wbt.store.repositories.ProductRepository;
import com.wbt.store.repositories.specifications.ProductSpec;
import com.wbt.store.services.ProductService;
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
}
