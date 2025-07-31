package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<Product> products = new HashSet<>();
}
