package com.restaurant.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealGroupResponse {
    private Long id;
    private String name;
    private List<MealResponse> menu;
    private Long restaurantId;
}
