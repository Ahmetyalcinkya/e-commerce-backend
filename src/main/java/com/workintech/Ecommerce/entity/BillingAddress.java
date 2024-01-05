package com.workintech.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing_address", schema = "ecommerce")
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private long phone;

    @Column(name = "title")
    private String title;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "address")
    private String address;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_billing_address", schema = "ecommerce",
            joinColumns = @JoinColumn(name = "billing_address_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public void addUser(User user){
        if(users == null){
            users = new ArrayList<>();
        }
        users.add(user);
    }

}

