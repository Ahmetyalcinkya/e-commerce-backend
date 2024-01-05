package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.AddressResponse;
import com.workintech.Ecommerce.entity.BillingAddress;
import com.workintech.Ecommerce.service.BillingAddressService;
import com.workintech.Ecommerce.service.UserService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/billing-address")
public class BillingAddressController {

    private BillingAddressService billingAddressService;
    private UserService userService;

    @Autowired
    public BillingAddressController(BillingAddressService billingAddressService, UserService userService) {
        this.billingAddressService = billingAddressService;
        this.userService = userService;
    }

    @GetMapping
    public List<AddressResponse> getAllBillingAddresses(){
        return Converter.findBillingAddresses(billingAddressService.getAllBillingAddresses());
    }
    @PostMapping
    public AddressResponse saveBillingAddress(@RequestBody BillingAddress address){
        List<BillingAddress> billingAddresses = billingAddressService.getAllBillingAddresses();
        if(billingAddresses.contains(address)){
            //TODO Throw exception
            throw new RuntimeException();
        }
        return Converter.findBillingAddress((userService.addBillingAddress(address)));
    }
    @DeleteMapping("/{id}")
    public AddressResponse deleteBillingAddress(@PathVariable long id){
        return Converter.findBillingAddress(billingAddressService.deleteBillingAddress(id));
    }
}
