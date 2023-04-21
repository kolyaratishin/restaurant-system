package com.restaurant.controller.dto;

import com.restaurant.controller.response.ReceiptMealResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
    private Long id;
    private List<ReceiptMealResponse> meals;
    private BigDecimal totalPrice;
}
