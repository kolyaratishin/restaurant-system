package com.restaurant.service;

import com.restaurant.model.MealGroup;
import com.restaurant.repository.MealGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<MealGroup> getAll(){
        return mealGroupRepository.findAll();
    }

    public Optional<MealGroup> getMealGroupByName(String name){
        return mealGroupRepository.findByName(name);
    }
}
