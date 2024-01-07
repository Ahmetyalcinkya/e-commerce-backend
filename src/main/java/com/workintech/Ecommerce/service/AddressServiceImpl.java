package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.Address;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.AddressRepository;
import com.workintech.Ecommerce.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address deleteAddress(long id) {
        Optional<Address> optionalAddress = addressRepository.findAddressByID(id);
        if(optionalAddress.isPresent()){
            addressRepository.delete(optionalAddress.get());
            return optionalAddress.get();
        }
        throw new ECommerceException(Constants.ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
