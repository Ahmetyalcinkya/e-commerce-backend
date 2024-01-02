package com.workintech.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "ecommerce")
public class Role { // implements Granted Authority OneToOne with User

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "authority")
    private String authority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;

    public void addUser(User user){
        if(users == null){
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
