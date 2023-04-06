package com.restaurant.controller.dto;

import com.restaurant.model.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private Long id;
    private String name;
    private TableStatus status;
    private Long restaurantId;
}
