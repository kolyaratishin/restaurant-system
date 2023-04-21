package com.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receipt_meal")
@IdClass(ReceiptMealId.class)
public class ReceiptMeal {
    @Id
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @Id
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    private Long amount;

    public BigDecimal getTotalPrice() {
        return meal.getPrice().multiply(BigDecimal.valueOf(amount));
    }
}
