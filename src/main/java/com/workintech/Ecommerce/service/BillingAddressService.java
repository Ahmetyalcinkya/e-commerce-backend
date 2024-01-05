package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.Address;
import com.workintech.Ecommerce.entity.BillingAddress;

import java.util.List;

public interface BillingAddressService {
    List<BillingAddress> getAllBillingAddresses();
    BillingAddress saveBillingAddress(BillingAddress address);
    BillingAddress deleteBillingAddress(long id);
}
