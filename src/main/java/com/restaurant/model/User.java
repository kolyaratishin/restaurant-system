package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "restaurant_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> employees;

    @OneToOne(cascade = CascadeType.ALL)
    private User admin;

    public void addEmployee(User user){
        employees.add(user);
        user.setAdmin(this);
    }

    public void removeEmployee(User user){
        employees.remove(user);
        user.setAdmin(null);
    }

    public void clearEmployees(){
        employees.clear();
    }
}
