package com.Shopping.App.service.repo;

import com.Shopping.App.schema.OrderDetail;
import com.Shopping.App.schema.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction findTransactionByOrderAndStatus(OrderDetail order,String status);
}
