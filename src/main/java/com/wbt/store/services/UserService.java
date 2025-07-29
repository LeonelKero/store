package com.wbt.store.services;

import com.wbt.store.entities.Product;
import com.wbt.store.repositories.ProductRepository;
import com.wbt.store.repositories.UserRepository;
import com.wbt.store.repositories.specifications.ProductSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public List<Product> findProductsBySpecification(final String name, final BigDecimal minPrice, final BigDecimal maxPrice) {
        Specification<Product> specification = Specification.where(null);
        if (name != null) specification.and(ProductSpec.hasName(name));
        if (minPrice != null) specification.and(ProductSpec.hasPriceEqualOrGreaterThen(minPrice));
        if (maxPrice != null) specification.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        return this.productRepository.findAll(specification);
    }

    public List<Product> sortedProductByName() {
        Sort sort = Sort.by("name")
                .and(Sort.by("price").descending());
        return this.productRepository.findAll(sort);
    }

    public Page<Product> fetchPagedProducts(final Integer page, final Integer size) {
        return this.productRepository.findAll(PageRequest.of(page, size));
    }
}
