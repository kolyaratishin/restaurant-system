package com.restaurant.controller.filter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.controller.dto.TableDto;
import com.restaurant.controller.filter.util.BodyHttpServletRequestWrapper;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantTable;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TableHandler extends Handler {

    private final String TABLE_URI = "/api/table";
    private final RestaurantTableService tableService;
    private final RestaurantService restaurantService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean authoritiesCheck(String username, String password, BodyHttpServletRequestWrapper request) {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(TABLE_URI)) {
            String method = request.getMethod();
            if (method.equals("GET") || method.equals("DELETE")) {
                return checkById(username, requestURI);
            } else if (method.equals("POST")) {
                if (requestURI.startsWith(TABLE_URI + "/status")) {
                    return checkById(username, requestURI);
                } else {
                    TableDto tableDto = getTableDto(request);
                    Restaurant restaurant = restaurantService.getRestaurantById(tableDto.getRestaurantId());
                    return restaurant.getOwner().getUsername().equals(username);
                }
            }
        }
        return false;
    }

    private boolean checkById(String username, String requestURI) {
        Long tableId = getTableIdFromPath(requestURI);
        RestaurantTable table = tableService.getTableById(tableId);
        return table.getRestaurant().getOwner().getUsername().equals(username);
    }

    private Long getTableIdFromPath(String requestURI) {
        String[] split = requestURI.split("/");
        String restUri = split[split.length - 1];
        return Long.valueOf(restUri.split("/")[0]);
    }

    private TableDto getTableDto(BodyHttpServletRequestWrapper requestWrapper) {
        try {
            return objectMapper.readValue(requestWrapper.getRequestBody(), TableDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
