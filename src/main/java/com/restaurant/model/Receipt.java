package com.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Meal> meals;

    @OneToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;
}
