package com.restaurant.repository;

import com.restaurant.model.MealGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealGroupRepository extends JpaRepository<MealGroup, Long> {
    Optional<MealGroup> findByName(String name);
}
