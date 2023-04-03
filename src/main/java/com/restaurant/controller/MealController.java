package com.restaurant.controller;

import com.restaurant.controller.dto.MealDto;
import com.restaurant.model.Meal;
import com.restaurant.service.MealService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    private final ModelMapper modelMapper;
    
    @PostMapping("/{restaurantId}")
    public MealDto save(@RequestBody Meal meal, @PathVariable(value = "restaurantId") Long restaurantId) {
        Meal savedMeal = mealService.save(meal, restaurantId);
        return modelMapper.map(meal, MealDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        mealService.deleteById(id);
    }
}
