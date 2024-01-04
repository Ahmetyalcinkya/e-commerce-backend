package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.requestDto.LoginRequest;
import com.workintech.Ecommerce.dto.requestDto.UserRequest;
import com.workintech.Ecommerce.dto.responseDto.LoginResponse;
import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.service.AuthenticationService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest){
        User user = authenticationService.register(userRequest.name(), userRequest.email(), userRequest.password(), userRequest.role());
        return Converter.findUser(user);
    }

    @GetMapping("/confirm")
    @ResponseBody
    public String confirm(@RequestParam(name = "emailToken") String emailToken){
        return authenticationService.confirmEmailToken(emailToken);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authenticationService.login(loginRequest.email(), loginRequest.password());
    }
}
