package com.restaurant.controller.filter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.controller.request.MealRequest;
import com.restaurant.model.Meal;
import com.restaurant.model.MealGroup;
import com.restaurant.service.MealGroupService;
import com.restaurant.service.MealService;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealHandler extends Handler {
    private final String MEAL_URI = "/api/meal";
    private final RestaurantService restaurantService;
    private final ObjectMapper objectMapper;
    private final MealGroupService mealGroupService;
    private final MealService mealService;

    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(MEAL_URI)) {
            String method = request.getMethod();
            if (method.equals("POST")) {
                MealRequest mealRequest = getMealRequest(request);
                MealGroup mealGroupById = mealGroupService.getMealGroupById(mealRequest.getMealGroupId());
                return mealGroupById.getRestaurant().getOwner().getUsername().equals(username);
            } else if (method.equals("DELETE") || method.equals("PUT")) {
                String restUri = requestURI.split(MEAL_URI + "/")[1];
                Long mealId = Long.valueOf(restUri.split("/")[0]);
                Meal meal = mealService.getMealById(mealId);
                return meal.getMealGroup().getRestaurant().getOwner().getUsername().equals(username);
            }
        }
        return false;
    }

    private MealRequest getMealRequest(BodyHttpServletRequestWrapper requestWrapper) {
        try {
            return objectMapper.readValue(requestWrapper.getRequestBody(), MealRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
