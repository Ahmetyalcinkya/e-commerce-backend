package com.workintech.Ecommerce.controller;

import com.workintech.Ecommerce.dto.responseDto.RoleResponse;
import com.workintech.Ecommerce.service.RoleService;
import com.workintech.Ecommerce.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleResponse> getRoles(){
        return Converter.findRoles(roleService.getRoles());
    }
}
