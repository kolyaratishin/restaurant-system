package com.restaurant.service;


import com.restaurant.model.Order;
import com.restaurant.model.Receipt;
import com.restaurant.model.RestaurantTable;
import com.restaurant.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order geOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
