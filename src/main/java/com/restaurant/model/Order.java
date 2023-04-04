package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    @ManyToMany
    private List<Meal> meals;

    private LocalDateTime createdAt;

    public void addMeals(List<Meal> meals){
        this.meals = new ArrayList<>();
        this.meals.addAll(meals);
    }

    public BigDecimal getTotalPrice(){
        if(meals != null) {
            return meals.stream()
                    .map(Meal::getPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }
}
