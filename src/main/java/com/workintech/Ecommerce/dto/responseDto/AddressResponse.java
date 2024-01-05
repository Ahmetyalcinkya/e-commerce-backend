package com.workintech.Ecommerce.dto.responseDto;

public record AddressResponse(String name, String surname,long phone, String title,
                              String city, String district, String neighborhood,
                              String address) {
}
