package com.restaurant.service;

import com.restaurant.model.*;
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
        table.createReceipt();
        table.setStatus(TableStatus.FREE);
        return tableRepository.save(table);
    }

    public void deleteById(Long id){
        tableRepository.deleteById(id);
    }

    public RestaurantTable getTableById(Long id){
        return tableRepository.findById(id).orElseThrow();
    }

    public RestaurantTable changeStatus(Long id, TableStatus status){
        RestaurantTable tableById = getTableById(id);
        tableById.setStatus(status);
        return tableRepository.save(tableById);
    }
}
