package com.Shopping.App.dtoResponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class UserOrder {
    Long order_id;
    Long user_id;
    int quantity;
    private int amount;
    private String coupon;
}
