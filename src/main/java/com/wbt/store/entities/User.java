package com.wbt.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    public void addTag(final Tag tag) {
        this.tags.add(tag);
        tag.addUser(this);
    }

    public void removeTag(final Tag tag) {
        this.tags.remove(tag);
        tag.removeUser(this);
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private Set<Address> addresses = new HashSet<>();

    public void addAddresses(final Address address) {
        this.addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(final Address address) {
        this.addresses.remove(address);
        address.setUser(null);
    }

    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    @Builder.Default
    private Set<Product> wishlist = new HashSet<>();
}
