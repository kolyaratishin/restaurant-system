package com.restaurant.controller.filter.auth;

import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportHandler extends Handler {
    private final String IMPORT_URI = "/api/import/";
    private final RestaurantService restaurantService;

    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(IMPORT_URI)) {
            String method = request.getMethod();
            String restUri = requestURI.split(IMPORT_URI)[1];
            if (method.equals("POST")) {
                Long restaurantId = Long.valueOf(restUri.split("/")[1]);
                Restaurant restaurantById = restaurantService.getRestaurantById(restaurantId);
                return restaurantById.getOwner().getUsername().equals(username);
            }
        }
        return false;
    }
}
