package com.restaurant.service;

import com.restaurant.model.Order;
import com.restaurant.model.OrderMeal;
import com.restaurant.service.dto.MealsCountInOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderService orderService;

    public List<MealsCountInOrderDto> getAllMealsInOrders(Long restaurantId) {
        List<Order> orders = orderService.getAllOrders();
        List<OrderMeal> orderMeals = new ArrayList<>();
        orders.stream()
                .filter(order -> order.getRestaurant().getId().equals(restaurantId))
                .forEach(order -> orderMeals.addAll(order.getMeals()));
        return orderMeals.stream()
                .collect(toMap(OrderMeal::getName, OrderMeal::getAmount, Long::sum))
                .entrySet().stream()
                .map(entry -> new MealsCountInOrderDto(entry.getKey(), entry.getValue()))
                .collect(toList());
    }
}
