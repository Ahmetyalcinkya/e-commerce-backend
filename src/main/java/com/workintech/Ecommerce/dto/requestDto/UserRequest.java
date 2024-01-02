package com.workintech.Ecommerce.dto.requestDto;

import com.workintech.Ecommerce.entity.Role;

public record UserRequest(String name, String email, String password, String role) {
}
