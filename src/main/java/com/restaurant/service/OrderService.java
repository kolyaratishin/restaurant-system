package com.restaurant.service;


import com.restaurant.exception.EntityByIdNotFoundException;
import com.restaurant.model.Order;
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

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException(id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
