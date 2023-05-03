package com.restaurant.service;

import com.restaurant.exception.EntityByIdNotFoundException;
import com.restaurant.model.*;
import com.restaurant.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantTableService {
    private final RestaurantTableRepository tableRepository;

    public RestaurantTable save(RestaurantTable table){
        table.createReceipt();
        table.setStatus(TableStatus.FREE);
        return tableRepository.save(table);
    }

    public void deleteById(Long id){
        tableRepository.deleteById(id);
    }

    public RestaurantTable getTableById(Long id){
        return tableRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException(id));
    }

    public RestaurantTable changeStatus(Long id, TableStatus status){
        RestaurantTable tableById = getTableById(id);
        tableById.setStatus(status);
        return tableRepository.save(tableById);
    }
}
