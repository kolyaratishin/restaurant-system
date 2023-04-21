package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Receipt {

    @Id
    @Column(name = "table_id")
    private Long id;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceiptMeal> meals;

    @OneToOne
    @MapsId
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    public BigDecimal getTotalPrice(){
        if(meals != null) {
            return meals.stream()
                    .map(ReceiptMeal::getTotalPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }

    public void removeAllMeals(){
        this.meals.clear();
    }

    public void removeTable(){
        this.table.setReceipt(null);
        this.table = null;
    }

    public void addAllMeals(List<ReceiptMeal> meals){
        this.meals.addAll(meals);
    }

    public void addMeal(ReceiptMeal meal){
        this.meals.add(meal);
        meal.setReceipt(this);
    }

    public void removeMeal(ReceiptMeal receiptMeal){
        this.meals.remove(receiptMeal);
        receiptMeal.setReceipt(null);
        receiptMeal.setMeal(null);
    }

    public void addTable(RestaurantTable table){
        this.table = table;
        table.setReceipt(this);
    }
}
