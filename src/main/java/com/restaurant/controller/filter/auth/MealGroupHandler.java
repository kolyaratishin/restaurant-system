package com.restaurant.controller.filter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.controller.request.MealGroupRequest;
import com.restaurant.model.MealGroup;
import com.restaurant.model.Restaurant;
import com.restaurant.service.MealGroupService;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealGroupHandler extends Handler{

    private final String MEAL_GROUP_URI = "/api/group";
    private final RestaurantService restaurantService;

    private final ObjectMapper objectMapper;
    private final MealGroupService mealGroupService;

    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(MEAL_GROUP_URI)) {
            String method = request.getMethod();
            if (method.equals("POST")) {
                MealGroupRequest mealGroupRequest = getMealGroupRequest(request);
                Restaurant restaurant = restaurantService.getRestaurantById(mealGroupRequest.getRestaurantId());
                return restaurant.getOwner().getUsername().equals(username);
            } else if (method.equals("GET") || method.equals("DELETE") || method.equals("PUT")) {
                Long mealGroupId = getMealGroupIdFromPath(requestURI);
                MealGroup mealGroup = mealGroupService.getMealGroupById(mealGroupId);
                return mealGroup.getRestaurant().getOwner().getUsername().equals(username);
            }
        }
        return false;
    }

    private Long getMealGroupIdFromPath(String requestURI) {
        String restUri = requestURI.split(MEAL_GROUP_URI + "/")[1];
        return Long.valueOf(restUri.split("/")[0]);
    }

    private MealGroupRequest getMealGroupRequest(BodyHttpServletRequestWrapper requestWrapper) {
        try {
            return objectMapper.readValue(requestWrapper.getRequestBody(), MealGroupRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
