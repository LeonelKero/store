package com.wbt.store.specifications;

import com.wbt.store.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasName(final String name) {
        return (root, query, cb) ->
                name == null ? cb.conjunction() : cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasPriceLessThan(final BigDecimal price) {
        return (root, query, cb) ->
                price == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> isInCategoryId(final Byte id) {
        return (root, query, cb) ->
                id == null ? cb.conjunction() : cb.equal(root.join("category").get("id"), id);
    }

    public static Specification<Product> hasCategoryName(final String name) {
        return (root, query, cb) ->
                name == null ? cb.conjunction() : cb.equal(root.join("category").get("name"), name);
    }
}
