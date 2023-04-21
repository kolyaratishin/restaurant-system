package com.restaurant.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptMealResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long amount;
}
