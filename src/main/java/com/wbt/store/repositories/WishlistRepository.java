package com.wbt.store.repositories;

import com.wbt.store.entities.Wishlist;
import com.wbt.store.entities.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
}
