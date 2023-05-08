package com.restaurant.controller.filter.auth;

import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantHandler extends Handler {

    private final String RESTAURANT_URI = "/api/restaurant/";
    private final RestaurantService restaurantService;


    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(RESTAURANT_URI)) {
            String method = request.getMethod();
            String restUri = requestURI.split(RESTAURANT_URI)[1];
            if (method.equals("GET") || method.equals("DELETE")) {
                Long restaurantId = Long.valueOf(restUri.split("/")[0]);
                Restaurant restaurantById = restaurantService.getRestaurantById(restaurantId);
                return restaurantById.getOwner().getUsername().equals(username);
            }
        }
        return false;
    }
}
