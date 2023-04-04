package com.restaurant.controller;

import com.restaurant.controller.dto.ReceiptDto;
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

    @PostMapping("/{tableId}")
    public ReceiptDto save(@RequestBody Receipt receipt, @PathVariable(value = "tableId") Long tableId){
        Receipt savedReceipt = receiptService.save(receipt, tableId);
        return modelMapper.map(savedReceipt, ReceiptDto.class);
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
}
