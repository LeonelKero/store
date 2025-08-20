package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(
            orphanRemoval = true,
            mappedBy = "cart",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.MERGE})
    List<CartItem> items = new ArrayList<>();

    public void removeItem(final CartItem item) {
        this.items.remove(item);
        item.setCart(null);
    }

    public void addItem(final CartItem item) {
        this.items.add(item);
        item.setCart(this);
    }

    public BigDecimal calculatePrice() {
        return this.items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add );
    }
}
