package com.wbt.store.repositories;

import com.wbt.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCart_Id(UUID id);
}
