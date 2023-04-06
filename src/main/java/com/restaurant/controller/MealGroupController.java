package com.restaurant.controller;

import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.controller.response.MealGroupResponse;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.service.MealGroupService;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class MealGroupController {

    private final ModelMapper modelMapper;

    private final MealGroupService mealGroupService;
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public MealGroup getMealGroupById(@PathVariable(name = "id") Long id){
        return mealGroupService.getMealGroupById(id);
    }

    @PostMapping
    public MealGroupResponse save(@RequestBody MealGroupRequest mealGroupRequest) {
        Restaurant restaurantById = restaurantService.getRestaurantById(mealGroupRequest.getRestaurantId());
        MealGroup mealGroup = modelMapper.map(mealGroupRequest, MealGroup.class);
        mealGroup.setRestaurant(restaurantById);
        MealGroup savedMealGroup = mealGroupService.save(mealGroup);
        MealGroupResponse mealGroupResponse = modelMapper.map(savedMealGroup, MealGroupResponse.class);
        mealGroupResponse.setRestaurantId(savedMealGroup.getRestaurant().getId());
        return mealGroupResponse;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        mealGroupService.deleteById(id);
    }
}
