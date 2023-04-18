package com.restaurant.service;

import com.restaurant.controller.dto.UserDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.Role;
import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ADMIN);
        Restaurant restaurant = new Restaurant();
        user.setRestaurant(restaurant);
        userRepository.save(user);
        return user;
    }



}
