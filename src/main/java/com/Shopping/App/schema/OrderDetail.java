package com.Shopping.App.schema;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.Random;

@Data
@Entity
public class OrderDetail {
    @Id
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private int amount;
    private String coupon;
    private String OrderStatus;
    private Date date;

    public OrderDetail() {
        this.orderId = generateRandomOrderId();
    }

    private Long generateRandomOrderId() {
        Random random = new Random();
        return (long) (random.nextInt(900) + 100);
    }
}