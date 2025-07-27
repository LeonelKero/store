package com.wbt.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public void addUser(final User user) {
        this.users.add(user);
        user.addTag(this);
    }

    public void removeUser(final User user) {
        this.users.remove(user);
        user.removeTag(this);
    }
}
