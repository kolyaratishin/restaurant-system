package com.restaurant.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptAddMealRequest {
    private Long mealId;
    private Long receiptId;
}
