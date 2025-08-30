package com.wbt.store.services;

import com.wbt.store.entities.Product;
import com.wbt.store.filters.ProductFilter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> fetchProductByCriteria();

    List<Product> findProductsBySpecification(String name, BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> sortedProductByName();

    Page<Product> fetchPagedProducts(Integer page, Integer size);

    Page<Product> filteredProducts(ProductFilter filter);
}
