package com.restaurant.service;

import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import com.restaurant.service.dto.ImportMealDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;
    private final MealGroupService mealGroupService;


    public void storeToDatabase(ImportMealDto dto) {
        Meal meal = modelMapper.map(dto, Meal.class);
        MealGroup mealGroupById = mealGroupService.getMealGroupById(dto.getMealGroupId());
        meal.setMealGroup(mealGroupById);
        mealRepository.save(meal);
    }
}
