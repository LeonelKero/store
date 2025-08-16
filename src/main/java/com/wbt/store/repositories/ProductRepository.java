package com.wbt.store.repositories;

import com.wbt.store.dtos.ProductSummaryDTO;
import com.wbt.store.entities.Category;
import com.wbt.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository, JpaSpecificationExecutor<Product> {
    // Projection
    // List<ProductSummary> findByCategory(Category category);

    // Another projection
    // List<ProductSummaryDTO> findByCategory(Category category);

    @EntityGraph(attributePaths = {"category"})
    @Query("select p from Product p")
    List<Product> findByProductsWithCategory();

    List<Product> findByCategory_IdOrderByPriceAsc(Byte id);

    List<Product> findByCategory_Id(Byte id);
}
