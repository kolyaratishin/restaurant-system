package com.restaurant.controller;

import com.restaurant.controller.dto.TableDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantTable;
import com.restaurant.model.TableStatus;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;
    private final RestaurantService restaurantService;

    private final ModelMapper modelMapper;

    @PostMapping
    public TableDto save(@RequestBody TableDto tableDto) {
        Restaurant restaurantById = restaurantService.getRestaurantById(tableDto.getRestaurantId());
        RestaurantTable restaurantTable = modelMapper.map(tableDto, RestaurantTable.class);
        restaurantTable.setRestaurant(restaurantById);
        RestaurantTable savedTable = restaurantTableService.save(restaurantTable);
        TableDto tableResponse = modelMapper.map(savedTable, TableDto.class);
        tableResponse.setRestaurantId(savedTable.getRestaurant().getId());
        return tableResponse;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        restaurantTableService.deleteById(id);
    }

    @PostMapping("/status/{tableId}")
    public TableDto changeStatusToFree(@PathVariable(value = "tableId") Long tableId, @RequestParam("status")String status) {
        RestaurantTable table = restaurantTableService.changeStatus(tableId, TableStatus.valueOf(status));
        return modelMapper.map(table, TableDto.class);
    }

    @GetMapping("/{tableId}")
    public TableDto getTableById(@PathVariable(value = "tableId") Long tableId) {
        RestaurantTable table = restaurantTableService.getTableById(tableId);
        return modelMapper.map(table, TableDto.class);
    }
}
