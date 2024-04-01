package com.Shopping.App.schema;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    String product;
    private int ordered;
    private int price;
    private int available;
}