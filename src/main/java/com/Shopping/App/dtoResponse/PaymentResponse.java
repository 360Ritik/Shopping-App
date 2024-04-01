package com.Shopping.App.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long userId;
    private Long orderId;
    private String transactionId="Not Executed";
    private String status;
    private String description;

}
