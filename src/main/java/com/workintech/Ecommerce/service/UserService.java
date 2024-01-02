package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.requestDto.UserRequest;
import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    List<UserResponse> getAllUsers();
    User findUserByID(long id);
    UserResponse saveUser(User user);
    User deleteUser(long id);
}
