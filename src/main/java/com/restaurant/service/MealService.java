package com.restaurant.service;

import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService {

    private final RestaurantService restaurantService;
    private final MealRepository mealRepository;

    public Meal save(Meal meal, Long restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        meal.setRestaurant(restaurant);
        return mealRepository.save(meal);
    }

    public void deleteById(Long id){
        mealRepository.deleteById(id);
    }
}
