package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class MealGroup {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "mealGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> menu = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void removeAllMeals(){
        menu.clear();
    }

    public void removeMeal(Meal meal){
        menu.remove(meal);
    }
}
