package com.restaurant.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealsCountInOrderDto {
    private String name;
    private Long amount;
}
