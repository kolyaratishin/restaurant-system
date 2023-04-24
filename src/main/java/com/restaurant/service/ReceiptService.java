package com.restaurant.service;

import com.restaurant.controller.request.ReceiptAddMealRequest;
import com.restaurant.model.*;
import com.restaurant.repository.ReceiptMealRepository;
import com.restaurant.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final RestaurantTableService tableService;
    private final ReceiptRepository receiptRepository;
    private final OrderService orderService;
    private final MealService mealService;
    private final ReceiptMealRepository receiptMealRepository;

    public Receipt save(Receipt receipt) {
        RestaurantTable tableById = tableService.getTableById(receipt.getId());
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
                .map(receiptMeal -> new OrderMeal(receiptMeal.getMeal().getName(),
                        receiptMeal.getMeal().getPrice(),
                        receiptMeal.getAmount(),
                        receiptMeal.getMeal().getMealGroup().getName()))
                .toList();
        order.addMeals(orderMeals);
        order.setCreatedAt(LocalDateTime.now());
        order.setRestaurant(receiptById.getTable().getRestaurant());
        Order savedOrder = orderService.save(order);
        tableService.changeStatus(receiptById.getTable().getId(), TableStatus.FREE);
        deleteById(receiptId);
        return savedOrder;
    }

    public Receipt addMealToReceipt(ReceiptAddMealRequest request) {
        Receipt receipt = getReceiptById(request.getReceiptId());
        Meal meal = mealService.getMealById(request.getMealId());
        receipt.getMeals().stream()
                .filter(receiptMeal -> receiptMeal.getMeal().getId().equals(meal.getId()))
                .findFirst()
                .ifPresentOrElse((receiptMeal) -> {
                    receiptMeal.setAmount(receiptMeal.getAmount() + 1);
                }, () -> {
                    ReceiptMeal receiptMeal = new ReceiptMeal();
                    receiptMeal.setReceipt(receipt);
                    receiptMeal.setMeal(meal);
                    receiptMeal.setAmount(1L);
                    ReceiptMeal savedReceiptMeal = receiptMealRepository.save(receiptMeal);
                    receipt.addMeal(savedReceiptMeal);
                });
        return save(receipt);
    }

    public Receipt removeMealFromReceipt(Long receiptId, Long mealId) {
        Receipt receipt = getReceiptById(receiptId);
        Meal meal = mealService.getMealById(mealId);
        receipt.getMeals().stream()
                .filter(receiptMeal -> receiptMeal.getMeal().getId().equals(meal.getId()))
                .findFirst()
                .ifPresent((receiptMeal) -> {
                    receiptMeal.setAmount(receiptMeal.getAmount() - 1);
                    if(receiptMeal.getAmount() <= 0){
                        receipt.removeMeal(receiptMeal);
                    }
                });
        return save(receipt);
    }
}
