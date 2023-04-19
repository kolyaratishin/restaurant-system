package com.restaurant.controller.response;

import com.restaurant.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private Role role;
    private Long restaurantId;
}
