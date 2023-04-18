package com.restaurant.controller.response;

import com.restaurant.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    private String username;
    private String password;
    private Role role;
    private Long restaurantId;
}
