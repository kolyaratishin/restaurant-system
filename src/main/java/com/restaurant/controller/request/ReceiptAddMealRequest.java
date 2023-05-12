package com.restaurant.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptAddMealRequest {
    @NotNull
    private Long mealId;
    @NotNull
    private Long receiptId;
}
