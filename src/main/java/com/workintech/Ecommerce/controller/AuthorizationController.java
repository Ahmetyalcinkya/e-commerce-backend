package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.requestDto.LoginRequest;
import com.workintech.Ecommerce.dto.requestDto.UserRequest;
import com.workintech.Ecommerce.dto.responseDto.LoginResponse;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRequest userRequest){
        return authenticationService.register(userRequest.name(), userRequest.email(), userRequest.password(), userRequest.role());
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
//TODO Set token to database
        return authenticationService.login(loginRequest.email(), loginRequest.password());
    }
}