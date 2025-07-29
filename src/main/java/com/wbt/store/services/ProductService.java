package com.wbt.store.services;

import com.wbt.store.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> fetchProductByCriteria();
}
