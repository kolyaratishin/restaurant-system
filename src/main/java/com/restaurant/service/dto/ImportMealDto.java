package com.restaurant.service.dto;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportMealDto {
    @Parsed(field = "name")
    private String name;
    @Parsed(field = "price")
    private BigDecimal price;
    @Parsed(field = "mealGroupName")
    private String mealGroupName;
}
