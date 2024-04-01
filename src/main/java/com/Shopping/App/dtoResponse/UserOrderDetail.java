package com.Shopping.App.dtoResponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserOrderDetail {
    private Long orderId;
    private int amount;
    private Date date;
    private String coupon;
    private String transactionId;
    private String status;
}