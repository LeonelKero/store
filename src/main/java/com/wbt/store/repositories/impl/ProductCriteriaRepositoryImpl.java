package com.wbt.store.repositories.impl;

import com.wbt.store.entities.Product;
import com.wbt.store.repositories.ProductCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        final var cb = this.entityManager.getCriteriaBuilder();
        final var cq = cb.createQuery(Product.class);
        final var root = cq.from(Product.class);

        final var predicates = new ArrayList<Predicate>();
        if (name != null) predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        if (minPrice != null) predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        if (maxPrice != null) predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));

        cq.select(root).where(predicates.toArray(new Predicate[predicates.toArray().length]));

        return this.entityManager.createQuery(cq).getResultList();
    }
}
