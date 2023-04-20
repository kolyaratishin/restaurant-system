package com.restaurant.service;

import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.service.dto.ImportMealDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final ModelMapper modelMapper;
    private final MealGroupService mealGroupService;
    private final RestaurantService restaurantService;


    public void storeToDatabase(ImportMealDto dto, Long restaurantId) {
        Meal meal = modelMapper.map(dto, Meal.class);
        mealGroupService.getMealGroupByName(dto.getMealGroupName()).ifPresentOrElse((item) -> {
            item.addMeal(meal);
            mealGroupService.save(item);
        }, () -> {
            MealGroup mealGroup = new MealGroup();
            mealGroup.setName(dto.getMealGroupName());
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            mealGroup.setRestaurant(restaurant);
            mealGroup.addMeal(meal);
            mealGroupService.getMealGroupByName(mealGroup.getName());
            mealGroupService.save(mealGroup);
        });
    }
}
