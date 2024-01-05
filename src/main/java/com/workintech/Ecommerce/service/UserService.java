package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.Address;
import com.workintech.Ecommerce.entity.BillingAddress;
import com.workintech.Ecommerce.entity.Token;
import com.workintech.Ecommerce.entity.User;

import java.util.List;


public interface UserService {

    List<UserResponse> getAllUsers();
    User findUserByID(long id);
    void findUserByEmail(String email);
    UserResponse saveUser(User user);
    void updateUser(Token token, User user);
    User deleteUser(long id);
    int enableUser(String email);
    String getAuthenticatedUser();
    BillingAddress addBillingAddress(BillingAddress billingAddress);
    Address addAddress(Address address);
}
