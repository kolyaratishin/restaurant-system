package com.restaurant.controller;

import com.restaurant.controller.request.MealRequest;
import com.restaurant.controller.request.UpdateMealRequest;
import com.restaurant.controller.response.MealResponse;
import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.service.MealGroupService;
import com.restaurant.service.MealService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    private final MealGroupService mealGroupService;

    private final ModelMapper modelMapper;

    @PostMapping
    public MealResponse save(@RequestBody MealRequest mealRequest) {
        MealGroup mealGroupById = mealGroupService.getMealGroupById(mealRequest.getMealGroupId());
        Meal meal = modelMapper.map(mealRequest, Meal.class);
        meal.setMealGroup(mealGroupById);
        Meal savedMeal = mealService.save(meal);
        MealResponse mealResponse = modelMapper.map(savedMeal, MealResponse.class);
        mealResponse.setMealGroupId(savedMeal.getMealGroup().getId());
        return mealResponse;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        mealService.deleteById(id);
    }

    @PutMapping("/{id}")
    public MealResponse updateMeal(@RequestBody UpdateMealRequest request, @PathVariable(value = "id") Long id) {
        Meal meal = mealService.updateMeal(id, request);
        MealResponse mealResponse = modelMapper.map(meal, MealResponse.class);
        mealResponse.setMealGroupId(meal.getMealGroup().getId());
        return mealResponse;
    }
}
