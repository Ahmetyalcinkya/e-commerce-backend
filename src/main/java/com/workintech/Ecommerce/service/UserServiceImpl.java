package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.ProductResponse;
import com.workintech.Ecommerce.dto.responseDto.UserResponse;
import com.workintech.Ecommerce.entity.*;
import com.workintech.Ecommerce.repository.UserRepository;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not valid!"));
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
        //TODO Throw exception -> Kullanıcı yok
        return null;
    }

    @Override
    public void findUserByEmail(String email){
       boolean userExist = userRepository.findUserByEmail(email).isPresent();
       if(userExist){
           //TODO Throw exception
           throw new IllegalStateException("Email already taken.");
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
            //TODO Throw exception
            return null;
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
        //TODO Throw exception
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        user.addBillingAddress(billingAddress);
        userRepository.save(user);
        return billingAddress;
    }
    @Override
    public Address addAddress(Address address) {
        String userEmail = getAuthenticatedUser();
        //TODO Throw exception
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        address.setUser(user);
        user.addAddress(address);
        addressService.saveAddress(address);
        return address;
    }
}
