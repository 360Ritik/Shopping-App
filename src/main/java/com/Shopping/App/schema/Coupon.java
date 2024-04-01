package com.Shopping.App.schema;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private String coupon;
    private int discount;
}
