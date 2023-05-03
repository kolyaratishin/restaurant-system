package com.restaurant.service;

import com.restaurant.controller.request.UpdateMealRequest;
import com.restaurant.exception.EntityByIdNotFoundException;
import com.restaurant.model.Meal;
import com.restaurant.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public Meal save(Meal meal){
        return mealRepository.save(meal);
    }

    public void deleteById(Long id){
        mealRepository.deleteById(id);
    }

    public Meal getMealById(Long id){
        return mealRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException(id));
    }

    public Meal updateMeal(Long id, UpdateMealRequest request){
        Meal mealById = getMealById(id);
        mealById.setName(request.getName());
        mealById.setPrice(request.getPrice());
        return save(mealById);
    }
}
