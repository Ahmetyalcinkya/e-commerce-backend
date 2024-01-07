package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.BillingAddress;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.BillingAddressRepository;
import com.workintech.Ecommerce.util.Constants;
import com.workintech.Ecommerce.util.validation.ECommerceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BillingAddressServiceImpl implements BillingAddressService {

    private BillingAddressRepository billingAddressRepository;
    @Autowired
    public BillingAddressServiceImpl(BillingAddressRepository billingAddressRepository) {
        this.billingAddressRepository = billingAddressRepository;
    }

    @Override
    public List<BillingAddress> getAllBillingAddresses() {
        return billingAddressRepository.findAll();
    }

    @Override
    public BillingAddress saveBillingAddress(BillingAddress address) {
        ECommerceValidation.checkString(address.getName(), "Name",20);
        ECommerceValidation.checkString(address.getSurname(), "Surname",30);
        ECommerceValidation.checkString(address.getTitle(), "Title",20);
        ECommerceValidation.checkString(address.getTitle(), "City",20);
        ECommerceValidation.checkString(address.getTitle(), "District",30);
        ECommerceValidation.checkString(address.getTitle(), "Neighborhood",30);
        ECommerceValidation.checkString(address.getTitle(), "Address",150);
        ECommerceValidation.checkPhone(address.getPhone());
        return billingAddressRepository.save(address);
    }

    @Override
    public BillingAddress deleteBillingAddress(long id) {
        Optional<BillingAddress> optionalAddress = billingAddressRepository.findAddressByID(id);
        if(optionalAddress.isPresent()){
            billingAddressRepository.delete(optionalAddress.get());
            return optionalAddress.get();
        }
        throw new ECommerceException(Constants.ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
