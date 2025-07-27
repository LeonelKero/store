package com.wbt.store.repositories;

import com.wbt.store.dtos.ProductSummary;
import com.wbt.store.dtos.ProductSummaryDTO;
import com.wbt.store.entities.Category;
import com.wbt.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Projection
    // List<ProductSummary> findByCategory(Category category);

    // Another projection
    List<ProductSummaryDTO> findByCategory(Category category);
}
