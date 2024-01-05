package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.BillingAddress;
import com.workintech.Ecommerce.repository.BillingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return billingAddressRepository.save(address);
    }

    @Override
    public BillingAddress deleteBillingAddress(long id) {
        Optional<BillingAddress> optionalAddress = billingAddressRepository.findAddressByID(id);
        if(optionalAddress.isPresent()){
            billingAddressRepository.delete(optionalAddress.get());
            return optionalAddress.get();
        }
        //TODO Throw exception
        return null;
    }
}
