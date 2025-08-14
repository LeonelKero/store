package com.wbt.store.repositories;

import com.wbt.store.entities.Address;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @EntityGraph(attributePaths = {"user"})
    @Query("select a from Address a where a.id = :id")
    Address getAddress(@Param("id") Long id);
}
