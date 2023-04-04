package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Meal> meals;

    private LocalDateTime createdAt;

    public void addMeals(List<Meal> meals){
        this.meals = new ArrayList<>();
        this.meals.addAll(meals);
    }
}
