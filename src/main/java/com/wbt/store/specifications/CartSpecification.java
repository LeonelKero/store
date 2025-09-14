package com.wbt.store.specifications;

import com.wbt.store.entities.Cart;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CartSpecification {
    public static Specification<Cart> hasDate(final LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) return cb.conjunction();
            return cb.equal(root.get("createdDate"), date);
        };
    }

    public static Specification<Cart> hasDate(final LocalDateTime date, final Integer range) {
        return (root, query, cb) -> {
            if (date == null) return cb.conjunction();
            final var start = date.minusDays(range);
            final var end = date.plusDays(range);

            return cb.between(root.get("createdDate"), start, end);
        };
    }

    public static Specification<Cart> hasProductWithName(final String name) {
        return (root, query, cb) -> {
            if (name == null) return cb.conjunction();

            query.distinct(true);
            return cb.equal(root.join("items").join("product").get("name"), name);
        };
    }
}
