package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.service.UserService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponse findUserByID(@PathVariable long id){
        return Converter.findUser(userService.findUserByID(id));
    }

    @PostMapping("/")
    public UserResponse saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable long id){
        return Converter.findUser(userService.deleteUser(id));
    }
}
