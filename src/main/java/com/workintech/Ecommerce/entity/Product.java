package com.workintech.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", schema = "ecommerce")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "rating")
    private double rating;

    @Column(name = "sell_count")
    private int sellCount;

    @Column(name = "stock")
    private int stock;

    @Column(name = "images")
    private String[] images;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
    CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;
}
