package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.Role;
import com.workintech.Ecommerce.repository.RoleRepository;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
}
