package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "restaurant_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;
}
