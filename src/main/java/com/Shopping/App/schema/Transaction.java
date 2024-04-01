package com.Shopping.App.schema;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@Entity
@Data
public class Transaction {
    @Id
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderDetail order;
    private Date transactionDate;
    private double amount;
    private String status;

    public Transaction() {
        this.transactionId = generateRandomTransactionId();
    }

    private String generateRandomTransactionId() {
            return "tran01010000" + (int) (Math.random() * 10);
    }

}