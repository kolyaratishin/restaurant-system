package com.restaurant.service;

import com.restaurant.exception.EntityByIdNotFoundException;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurantById(Long id){
        return restaurantRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException(id));
    }

    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public void deleteById(Long id){
        restaurantRepository.deleteById(id);
    }
}
