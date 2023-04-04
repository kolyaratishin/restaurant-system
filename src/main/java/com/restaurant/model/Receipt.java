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

    @OneToMany
    private List<Meal> meals;

    @OneToOne
    @MapsId
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    public BigDecimal getTotalPrice(){
        if(meals != null) {
            return meals.stream()
                    .map(Meal::getPrice)
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

    public void addAllMeals(List<Meal> meals){
        this.meals.addAll(meals);
    }
}
