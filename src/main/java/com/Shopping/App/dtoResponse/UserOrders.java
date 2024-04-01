package com.Shopping.App.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserOrders {
    private Long orderId;
    private int amount;
    private Date date;
    private String coupon;
}
