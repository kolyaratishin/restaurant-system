package com.restaurant.controller;

import com.restaurant.controller.dto.RestaurantDto;
import com.restaurant.controller.dto.TableDto;
import com.restaurant.controller.response.MealGroupResponse;
import com.restaurant.controller.response.MealResponse;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void deleteById(@PathVariable(value = "id") Long id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("/{restaurantId}/tables")
    public List<TableDto> getAllTables(@PathVariable(value = "restaurantId") Long id) {
        Restaurant restaurantById = restaurantService.getRestaurantById(id);
        return restaurantById.getTables()
                .stream()
                .map(table -> modelMapper.map(table, TableDto.class))
                .toList();
    }

    @GetMapping("/{restaurantId}/menu")
    public List<MealGroupResponse> getMenu(@PathVariable(value = "restaurantId") Long id) {
        Restaurant restaurantById = restaurantService.getRestaurantById(id);
        return restaurantById.getMealGroups()
                .stream()
                .map(mealGroup -> {
                    MealGroupResponse response = modelMapper.map(mealGroup, MealGroupResponse.class);
                    response.setMenu(mealGroup.getMeals().stream()
                            .map(meal -> modelMapper.map(meal, MealResponse.class))
                            .toList());
                    response.setRestaurantId(mealGroup.getRestaurant().getId());
                    return response;
                })
                .toList();
    }
}
