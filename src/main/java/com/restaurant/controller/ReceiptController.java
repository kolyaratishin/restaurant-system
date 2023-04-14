package com.restaurant.controller;

import com.restaurant.controller.dto.OrderDto;
import com.restaurant.controller.dto.ReceiptDto;
import com.restaurant.controller.request.ReceiptAddMealRequest;
import com.restaurant.model.Meal;
import com.restaurant.model.Order;
import com.restaurant.model.Receipt;
import com.restaurant.service.MealService;
import com.restaurant.service.OrderService;
import com.restaurant.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;
    private final MealService mealService;
    private final OrderService orderService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ReceiptDto getReceiptById(@PathVariable(value = "id") Long id) {
        Receipt receipt = receiptService.getReceiptById(id);
        ReceiptDto receiptDto = modelMapper.map(receipt, ReceiptDto.class);
        receiptDto.setTotalPrice(receipt.getTotalPrice());
        return receiptDto;
    }

    @PutMapping("/{id}")
    public ReceiptDto update(@RequestBody ReceiptDto receiptDto, @PathVariable(value = "id") Long receiptId) {
        Receipt receipt = modelMapper.map(receiptDto, Receipt.class);
        Receipt savedReceipt = receiptService.update(receipt, receiptId);
        ReceiptDto response = modelMapper.map(savedReceipt, ReceiptDto.class);
        response.setTotalPrice(savedReceipt.getTotalPrice());
        return response;
    }

    @PostMapping("/meal")
    public ReceiptDto addMealToReceipt(@RequestBody ReceiptAddMealRequest request) {
        Receipt receipt = receiptService.getReceiptById(request.getReceiptId());
        Meal meal = mealService.getMealById(request.getMealId());
        receipt.addMeal(meal);
        Receipt savedReceipt = receiptService.save(receipt);
        ReceiptDto response = modelMapper.map(savedReceipt, ReceiptDto.class);
        response.setTotalPrice(savedReceipt.getTotalPrice());
        return response;
    }

    @DeleteMapping("/{receiptId}/meal/{mealId}")
    public ReceiptDto removeMealFromReceipt(@PathVariable(value = "receiptId") Long receiptId,
                                            @PathVariable(value = "mealId") Long mealId) {
        Receipt receipt = receiptService.getReceiptById(receiptId);
        Meal meal = mealService.getMealById(mealId);
        receipt.removeMeal(meal);
        Receipt savedReceipt = receiptService.save(receipt);
        ReceiptDto response = modelMapper.map(savedReceipt, ReceiptDto.class);
        response.setTotalPrice(savedReceipt.getTotalPrice());
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id) {
        receiptService.deleteById(id);
    }

    @PostMapping("/count/{id}")
    public OrderDto countTheReceipt(@PathVariable(value = "id") Long receiptId) {
        Order order = receiptService.countTheReceipt(receiptId);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }

    @GetMapping("/orders")
    public List<OrderDto> getAllOrder() {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream()
                .map(order -> {
                            OrderDto dto = modelMapper.map(order, OrderDto.class);
                            dto.setTotalPrice(order.getTotalPrice());
                            return dto;
                        }
                )
                .toList();
    }
}
