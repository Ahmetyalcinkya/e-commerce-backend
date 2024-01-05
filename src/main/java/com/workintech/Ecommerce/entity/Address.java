package com.workintech.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address", schema = "ecommerce")
public class Address {

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

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
}
