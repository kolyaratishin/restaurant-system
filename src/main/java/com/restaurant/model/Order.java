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
    @ElementCollection
    @CollectionTable(name = "order_meals", joinColumns = @JoinColumn(name = "order_id"), foreignKey = @ForeignKey(name = "order_meal_orders_fk"))
    private List<OrderMeal> meals;

    private LocalDateTime createdAt;

    public void addMeals(List<OrderMeal> meals){
        this.meals = new ArrayList<>();
        this.meals.addAll(meals);
    }

    public BigDecimal getTotalPrice(){
        if(meals != null) {
            return meals.stream()
                    .map(OrderMeal::getPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }
}
