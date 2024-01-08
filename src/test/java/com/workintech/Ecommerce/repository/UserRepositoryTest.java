package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findUserByEmail() {
        Optional<User> user = userRepository.findUserByEmail("ahmetcan.yalcinkayaa@gmail.com");

        if (user.isPresent()){
            assertEquals("ahmetcan.yalcinkayaa@gmail.com", user.get().getEmail());
        }
    }
    @Test
    void findUserByEmailFail() {
        Optional<User> user = userRepository.findUserByEmail("ahmetcan.yalcinkayaa@gmail.com");

        if (user.isPresent()){
            assertNotEquals("@gmail.com", user.get().getEmail());
        }
    }
}