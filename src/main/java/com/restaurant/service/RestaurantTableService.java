package com.restaurant.service;

import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantTable;
import com.restaurant.repository.MealRepository;
import com.restaurant.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {
    private final RestaurantService restaurantService;
    private final RestaurantTableRepository tableRepository;

    public RestaurantTable save(RestaurantTable table, Long restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        table.setRestaurant(restaurant);
        return tableRepository.save(table);
    }

    public void deleteById(Long id){
        tableRepository.deleteById(id);
    }
}