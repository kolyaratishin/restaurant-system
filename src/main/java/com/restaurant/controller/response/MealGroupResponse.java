package com.restaurant.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealGroupResponse {
    private Long id;
    private String name;
    private Long restaurantId;
}
