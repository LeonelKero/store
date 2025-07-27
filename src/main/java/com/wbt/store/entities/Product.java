package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "FK_product_category", value = ConstraintMode.CONSTRAINT)
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public void addCategory(final Category category) {
        this.categories.add(category);
        category.addProduct(this);
    }

    @ManyToMany(mappedBy = "wishlist")
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
