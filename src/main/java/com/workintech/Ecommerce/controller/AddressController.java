package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.AddressResponse;
import com.workintech.Ecommerce.entity.Address;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.service.AddressService;
import com.workintech.Ecommerce.service.UserService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    private AddressService addressService;
    private UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public List<AddressResponse> getAllAddresses(){
        return Converter.findAddresses(addressService.getAllAddresses());
    }

    @PostMapping
    public AddressResponse saveAddress(@RequestBody Address address){
        return Converter.findAddress(userService.addAddress(address));
    }

    @DeleteMapping("/{id}")
    public AddressResponse deleteAddress(@PathVariable long id){
        return Converter.findAddress(addressService.deleteAddress(id));
    }
}
