package com.restaurant.service;

import com.restaurant.model.*;
import com.restaurant.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final RestaurantTableService tableService;
    private final ReceiptRepository receiptRepository;
    private final OrderService orderService;

    public Receipt save(Receipt receipt, Long tableId) {
        RestaurantTable tableById = tableService.getTableById(tableId);
        receipt.addTable(tableById);
        return receiptRepository.save(receipt);
    }

    public void deleteById(Long id) {
        Receipt receiptById = getReceiptById(id);
        receiptById.removeAllMeals();
        receiptRepository.delete(receiptById);
    }

    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id).orElseThrow();
    }

    public Receipt update(Receipt receipt, Long receiptId) {
        Receipt receiptById = getReceiptById(receiptId);
        receiptById.removeAllMeals();
        receiptById.addAllMeals(receipt.getMeals());
        return receiptRepository.save(receiptById);
    }

    public Order countTheReceipt(Long receiptId) {
        Receipt receiptById = getReceiptById(receiptId);
        Order order = new Order();
        List<OrderMeal> orderMeals = receiptById.getMeals().stream()
                .map(meal -> new OrderMeal(meal.getName(), meal.getPrice()))
                .toList();
        order.addMeals(orderMeals);
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderService.save(order);
        tableService.changeStatus(receiptById.getTable().getId(), TableStatus.FREE);
        deleteById(receiptId);
        return savedOrder;
    }
}
