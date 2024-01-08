package com.workintech.Ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.Ecommerce.entity.Role;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.service.UserService;
import com.workintech.Ecommerce.util.Converter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;


    @Test
    void saveUser() throws Exception {
        User user = new User();
        user.setName("Ali");
        user.setEmail("ali@gmail.com");
        user.setRole(new Role());
        user.setPassword("123456");

        when(userService.saveUser(user)).thenReturn(Converter.findUser(user));
        mockMvc.perform(post("/admin/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJSONToString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ali@gmail.com"));

        verify(userService).saveUser(user);
    }

    public static String convertJSONToString(Object o){
        try{
            return new ObjectMapper().writeValueAsString(o);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}