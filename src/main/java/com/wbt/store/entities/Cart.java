package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "item_ids")
    List<CartItem> items = new ArrayList<>();

    public void removeItem(final CartItem item) {
        this.items.remove(item);
        item.setCart(null);
    }

    public void addItem(final CartItem item) {
        this.items.add(item);
        item.setCart(this);
    }
}
