package com.wbt.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private Integer loyaltyPoints;

    @OneToOne(mappedBy = "profile")
    private User user;
}
