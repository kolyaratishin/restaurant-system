package com.restaurant.controller;

import com.restaurant.controller.dto.UserDto;
import com.restaurant.controller.request.AuthRequest;
import com.restaurant.controller.request.EmployeeRequest;
import com.restaurant.controller.response.UserResponse;
import com.restaurant.exception.RegistrationException;
import com.restaurant.model.User;
import com.restaurant.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registration(@RequestBody UserDto userDto) {
        if (userService.getUserByUsername(userDto.getUsername()).isEmpty()) {
            User registeredUser = userService.register(userDto);
            UserResponse response = modelMapper.map(registeredUser, UserResponse.class);
            response.setRestaurantId(registeredUser.getRestaurant().getId());
            return ResponseEntity.ok(response);
        }
        throw new RegistrationException("Such user with username=" + userDto.getUsername() + " is already exist");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        // Аутентифікація користувача
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Успішна аутентифікація
        // В цьому прикладі ми не генеруємо токен, оскільки використовуємо Basic автентифікацію
        // Ви можете додати код для генерації та повернення токена в залежності від ваших потреб

        // Повертаємо відповідь з успішним статусом
        return ResponseEntity.ok("Successfully logged in");
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username).
                map(user -> {
                    UserResponse response = modelMapper.map(user, UserResponse.class);
                    response.setRestaurantId(user.getRestaurant().getId());
                    return ResponseEntity.ok(response);
                })
                .orElseThrow();
    }

    @PostMapping("/employee")
    public ResponseEntity<UserResponse> addEmployee(@Valid @RequestBody EmployeeRequest request, @RequestParam(value = "username") String adminUsername) {
        User user = userService.addEmployee(request, adminUsername);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        response.setRestaurantId(user.getRestaurant().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<UserResponse>> getAllEmployees(@RequestParam(value = "username") String adminUsername) {
        List<User> employees = userService.getAllEmployees(adminUsername);
        return ResponseEntity.ok(
                employees.stream()
                        .map(employee -> modelMapper.map(employee, UserResponse.class))
                        .toList());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
    }
}
