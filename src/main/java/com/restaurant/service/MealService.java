package com.restaurant.service;

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
        return mealRepository.findById(id).orElseThrow();
    }
}
