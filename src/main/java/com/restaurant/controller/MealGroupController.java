package com.restaurant.controller;

import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.controller.response.MealGroupResponse;
import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.service.MealGroupService;
import com.restaurant.service.MealService;
import com.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class MealGroupController {

    private final ModelMapper modelMapper;

    private final MealGroupService mealGroupService;
    private final MealService mealService;
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public MealGroupResponse getMealGroupById(@PathVariable(name = "id") Long id) {
        MealGroup mealGroupById = mealGroupService.getMealGroupById(id);
        MealGroupResponse response = modelMapper.map(mealGroupById, MealGroupResponse.class);
        response.setRestaurantId(mealGroupById.getRestaurant().getId());
        return response;
    }

    @PostMapping
    public MealGroupResponse save(@Valid @RequestBody MealGroupRequest mealGroupRequest) {
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

    @DeleteMapping("{groupId}/meal/{mealId}")
    public void deleteMealFromMealGroup(@PathVariable(value = "groupId") Long groupId,
                                        @PathVariable(value = "mealId") Long mealId) {
        MealGroup mealGroupById = mealGroupService.getMealGroupById(groupId);
        Meal meal = mealService.getMealById(mealId);
        mealGroupById.removeMeal(meal);
        mealGroupService.save(mealGroupById);
    }

    @GetMapping
    public List<MealGroupResponse> getAllMealGroups() {
        return mealGroupService.getAll()
                .stream()
                .map(mealGroup -> modelMapper.map(mealGroup, MealGroupResponse.class))
                .toList();
    }
}
