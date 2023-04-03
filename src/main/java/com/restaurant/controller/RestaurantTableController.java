package com.restaurant.controller;

import com.restaurant.controller.dto.MealDto;
import com.restaurant.controller.dto.TableDto;
import com.restaurant.model.Meal;
import com.restaurant.model.RestaurantTable;
import com.restaurant.service.MealService;
import com.restaurant.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    private final ModelMapper modelMapper;

    @PostMapping("/{restaurantId}")
    public TableDto save(@RequestBody RestaurantTable table, @PathVariable(value = "restaurantId") Long restaurantId) {
        RestaurantTable savedTable = restaurantTableService.save(table, restaurantId);
        return modelMapper.map(table, TableDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        restaurantTableService.deleteById(id);
    }
}
