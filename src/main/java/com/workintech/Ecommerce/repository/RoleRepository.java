package com.workintech.Ecommerce.repository;

import com.workintech.Ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.authority = :authority")
    Optional<Role> findByAuthority(String authority);
}
