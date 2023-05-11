package com.restaurant.controller.filter.auth;

import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsHandler extends Handler{
    private final String STATISTICS_URI = "/api/statistics/";
    private final RestaurantService restaurantService;


    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(STATISTICS_URI)) {
            String method = request.getMethod();
            if (method.equals("GET")) {
                String restaurantIdParameter = request.getParameter("restaurantId");
                Long restaurantId = Long.valueOf(restaurantIdParameter);
                Restaurant restaurantById = restaurantService.getRestaurantById(restaurantId);
                return restaurantById.getOwner().getUsername().equals(username);
            }
        }
        return false;
    }
}
