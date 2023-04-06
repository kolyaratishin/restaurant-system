package com.restaurant.service;

import com.restaurant.model.MealGroup;
import com.restaurant.repository.MealGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealGroupService {
    private final MealGroupRepository mealGroupRepository;

    public MealGroup getMealGroupById(Long id){
        return mealGroupRepository.findById(id).orElseThrow();
    }

    public MealGroup save(MealGroup mealGroup) {
        return mealGroupRepository.save(mealGroup);
    }

    public void deleteById(Long id) {
        mealGroupRepository.deleteById(id);
    }
}
