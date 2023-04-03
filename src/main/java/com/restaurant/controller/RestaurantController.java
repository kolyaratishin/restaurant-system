package com.restaurant.controller;

import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable(value = "id") Long id){
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping
    public Restaurant save(@RequestBody Restaurant restaurant){
        return restaurantService.save(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        restaurantService.deleteById(id);
    }
}
