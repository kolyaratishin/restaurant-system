package com.restaurant.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealRequest {
    private String name;
    private BigDecimal price;
    private Long mealGroupId;
}
