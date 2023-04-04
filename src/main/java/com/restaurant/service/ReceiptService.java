package com.restaurant.service;

import com.restaurant.model.Receipt;
import com.restaurant.model.RestaurantTable;
import com.restaurant.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final RestaurantTableService tableService;
    private final ReceiptRepository receiptRepository;

    public Receipt save(Receipt receipt, Long tableId) {
        RestaurantTable tableById = tableService.getTableById(tableId);
        receipt.setTable(tableById);
        return receiptRepository.save(receipt);
    }

    public void deleteById(Long id) {
        receiptRepository.deleteById(id);
    }

    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id).orElseThrow();
    }

    public Receipt update(Receipt receipt, Long receiptId) {
        Receipt receiptById = getReceiptById(receiptId);
        receiptById.setMeals(receipt.getMeals());
        return receiptRepository.save(receiptById);
    }
}
