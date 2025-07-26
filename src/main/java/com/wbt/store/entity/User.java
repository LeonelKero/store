package com.wbt.store.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
}
