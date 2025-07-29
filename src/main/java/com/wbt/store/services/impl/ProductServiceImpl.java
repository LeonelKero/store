package com.wbt.store.services.impl;

import com.wbt.store.entities.Product;
import com.wbt.store.repositories.ProductRepository;
import com.wbt.store.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
}
