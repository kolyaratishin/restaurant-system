package com.restaurant.controller;

import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable(value = "id") Long id){
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    @PostMapping
    public RestaurantDto save(@RequestBody Restaurant restaurant){
        Restaurant savedRestaurant = restaurantService.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        restaurantService.deleteById(id);
    }
}
