package com.restaurant.repository;

import com.restaurant.model.ReceiptMeal;
import com.restaurant.model.ReceiptMealId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptMealRepository extends JpaRepository<ReceiptMeal, ReceiptMealId> {
}
