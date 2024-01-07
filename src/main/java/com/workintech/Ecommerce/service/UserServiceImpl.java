package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.*;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.UserRepository;
import com.workintech.Ecommerce.util.Constants;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {

    private UserRepository userRepository;
    private AddressService addressService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(Constants.USER_NOT_VALID));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return Converter.findUsers(userRepository.findAll());
    }

    @Override
    public User findUserByID(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new ECommerceException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public void findUserByEmail(String email){
       boolean userExist = userRepository.findUserByEmail(email).isPresent();
       if(userExist){
           throw new ECommerceException(Constants.EMAIL_TAKEN, HttpStatus.BAD_REQUEST);
       }
    }

    @Override
    public UserResponse saveUser(User user) {
        return Converter.findUser(userRepository.save(user));
    }

    @Override
    public void updateUser(Token token, User user) {
        user.setToken(token);
    }

    @Override
    public User deleteUser(long id) {
        User user = findUserByID(id);
        if(user == null){
            throw new ECommerceException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
        return user;
    }
    @Override
    public int enableUser(String email) {
       return userRepository.enableUser(email);
    }

    @Override
    public String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         return authentication.getName();
    }

    @Override
    public BillingAddress addBillingAddress(BillingAddress billingAddress) {
        String userEmail = getAuthenticatedUser();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() ->
                new ECommerceException(Constants.USER_NOT_FOUND + userEmail,HttpStatus.NOT_FOUND));

        user.addBillingAddress(billingAddress);
        userRepository.save(user);
        return billingAddress;
    }
    @Override
    public Address addAddress(Address address) {
        String userEmail = getAuthenticatedUser();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() ->
                new ECommerceException(Constants.USER_NOT_FOUND + userEmail,HttpStatus.NOT_FOUND));

        address.setUser(user);
        user.addAddress(address);
        addressService.saveAddress(address);
        return address;
    }
}
