package com.restaurant.service;

import com.restaurant.model.Order;
import com.restaurant.model.Receipt;
import com.restaurant.model.RestaurantTable;
import com.restaurant.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public Order countTheReceipt( Long receiptId) {
        Receipt receiptById = getReceiptById(receiptId);
        Order order = new Order();
        order.addMeals(receiptById.getMeals());
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderService.save(order);
        deleteById(receiptId);
        return order;
    }
}
