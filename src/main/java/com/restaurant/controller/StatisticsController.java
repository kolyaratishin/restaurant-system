package com.restaurant.controller;

import com.restaurant.service.StatisticsService;
import com.restaurant.service.dto.MealsCountInOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/meals/count/order")
    public List<MealsCountInOrderDto> getAllMealsInOrders(@RequestParam(value = "restaurantId")Long restaurantId) {
        return statisticsService.getAllMealsInOrders(restaurantId);
    }
}
