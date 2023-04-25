package com.restaurant.controller;

import com.restaurant.controller.dto.UserDto;
import com.restaurant.controller.request.EmployeeRequest;
import com.restaurant.controller.response.UserResponse;
import com.restaurant.model.User;
import com.restaurant.service.UserService;
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
    public UserResponse registration(@RequestBody UserDto userDto) {
        User registeredUser = userService.register(userDto);
        UserResponse response = modelMapper.map(registeredUser, UserResponse.class);
        response.setRestaurantId(registeredUser.getRestaurant().getId());
        return response;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {

        // Перевірка наявності заголовка Authorization
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        // Декодування та розпакування даних авторизації
        String encodedCredentials = authHeader.substring("Basic ".length()).trim();
        byte[] decodedCredentials = Base64.getDecoder().decode(encodedCredentials);
        String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
        String[] split = credentials.split(":", 2);
        String username = split[0];
        String password = split[1];

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
        User user = userService.getUserByUsername(username);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        response.setRestaurantId(user.getRestaurant().getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/employee")
    public ResponseEntity<UserResponse> addEmployee(@RequestBody EmployeeRequest request, @RequestParam(value = "username") String adminUsername) {
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
