package com.restaurant.service;

import com.restaurant.controller.dto.UserDto;
import com.restaurant.controller.request.EmployeeRequest;
import com.restaurant.model.Restaurant;
import com.restaurant.model.Role;
import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User addEmployee(EmployeeRequest request, String adminUsername) {
        User employee = new User();
        employee.setUsername(request.getUsername());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setRole(Role.USER);
        User adminUser = getUserByUsername(adminUsername);
        employee.setRestaurant(adminUser.getRestaurant());
        userRepository.save(employee);

        adminUser.addEmployee(employee);
        userRepository.save(adminUser);
        return employee;
    }

    public List<User> getAllEmployees(String adminUsername) {
        return getUserByUsername(adminUsername).getEmployees();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        User user = getUserById(id);
        if (user.getRole().equals(Role.ADMIN)) {
            user.clearEmployees();
            userRepository.deleteById(id);
        } else {
            User admin = user.getAdmin();
            admin.removeEmployee(user);
            userRepository.deleteById(id);
            userRepository.save(admin);
        }
    }
}
