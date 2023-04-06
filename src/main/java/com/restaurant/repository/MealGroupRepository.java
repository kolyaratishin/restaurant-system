package com.restaurant.repository;

import com.restaurant.model.MealGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealGroupRepository extends JpaRepository<MealGroup, Long> {
}
