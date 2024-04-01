package com.Shopping.App.dtoResponse;

import java.util.Date;

public interface UserOrderDetailProjection {

     Long getOrderId();
     int getAmount();
     Date getDate();
     String getCoupon();
     String getTransactionId();
     String getStatus();


}
