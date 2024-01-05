package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM ecommerce.address AS a WHERE a.id = :id",nativeQuery = true)
    Optional<Address> findAddressByID(long id);
}
