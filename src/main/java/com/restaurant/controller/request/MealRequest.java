package com.restaurant.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealRequest {
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long mealGroupId;
}
