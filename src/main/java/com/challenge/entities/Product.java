package com.challenge.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 10)
    private Integer id;
    @Column(unique = true, nullable = false, length = 11)
    private Integer SKU;
    @Column(nullable = false)
    private Integer code;
    @Column(length = 40, nullable = false)
    private String name;
    private String description;
    private String picture;
    private Double price;
    @Column(length = 3)
    private String currency;
}
