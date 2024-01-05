package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAllAddresses();
    Address saveAddress(Address address);
    Address deleteAddress(long id);

}
