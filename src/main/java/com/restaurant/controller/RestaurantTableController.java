package com.restaurant.controller;

import com.restaurant.controller.dto.TableDto;
import com.restaurant.model.RestaurantTable;
import com.restaurant.model.TableStatus;
import com.restaurant.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    private final ModelMapper modelMapper;

    @PostMapping("/{restaurantId}")
    public TableDto save(@RequestBody RestaurantTable table, @PathVariable(value = "restaurantId") Long restaurantId) {
        RestaurantTable savedTable = restaurantTableService.save(table, restaurantId);
        return modelMapper.map(table, TableDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        restaurantTableService.deleteById(id);
    }

    @PostMapping("/status/free/{tableId}")
    public TableDto changeStatusToFree(@PathVariable(value = "tableId") Long tableId) {
        RestaurantTable table = restaurantTableService.changeStatus(tableId, TableStatus.FREE);
        return modelMapper.map(table, TableDto.class);
    }

    @PostMapping("/status/reserved/{tableId}")
    public TableDto changeStatusToReserved(@PathVariable(value = "tableId") Long tableId) {
        RestaurantTable table = restaurantTableService.changeStatus(tableId, TableStatus.RESERVED);
        return modelMapper.map(table, TableDto.class);
    }

    @PostMapping("/status/busy/{tableId}")
    public TableDto changeStatusToBusy(@PathVariable(value = "tableId") Long tableId) {
        RestaurantTable table = restaurantTableService.changeStatus(tableId, TableStatus.BUSY);
        return modelMapper.map(table, TableDto.class);
    }

    @GetMapping("/{tableId}")
    public TableDto getTableById(@PathVariable(value = "tableId") Long tableId) {
        RestaurantTable table = restaurantTableService.getTableById(tableId);
        return modelMapper.map(table, TableDto.class);
    }
}
