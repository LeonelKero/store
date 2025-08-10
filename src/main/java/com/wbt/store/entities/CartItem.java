package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart_item",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"cart_id", "product_id"}
        ))
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public BigDecimal getTotalPrice() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
