package com.restaurant.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealGroupRequest {
    private String name;
    private Long restaurantId;
}
