package com.restaurant.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiptMealId implements Serializable {
    private Long receipt;
    private Long meal;
}
