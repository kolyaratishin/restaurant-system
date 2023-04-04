package com.restaurant.controller;

import com.restaurant.controller.dto.OrderDto;
import com.restaurant.controller.dto.ReceiptDto;
import com.restaurant.model.Order;
import com.restaurant.model.Receipt;
import com.restaurant.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ReceiptDto getReceiptById(@PathVariable(value = "id") Long id){
        Receipt receipt = receiptService.getReceiptById(id);
        ReceiptDto receiptDto = modelMapper.map(receipt, ReceiptDto.class);
        receiptDto.setTotalPrice(receipt.getTotalPrice());
        return receiptDto;
    }

    @PutMapping("/{receiptId}")
    public ReceiptDto update(@RequestBody Receipt receipt, @PathVariable(value = "receiptId") Long receiptId){
        Receipt savedReceipt = receiptService.update(receipt, receiptId);
        return modelMapper.map(savedReceipt, ReceiptDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        receiptService.deleteById(id);
    }

    @PostMapping("/count/{receiptId}")
    public OrderDto countTheReceipt(@PathVariable(value = "receiptId") Long receiptId){
        Order order = receiptService.countTheReceipt(receiptId);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }
}
