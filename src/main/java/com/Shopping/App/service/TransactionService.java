package com.Shopping.App.service;

import com.Shopping.App.schema.OrderDetail;
import com.Shopping.App.schema.Transaction;
import com.Shopping.App.service.repo.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private  final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public Transaction findTransactionByOrder(OrderDetail orderId,String status) {
            return transactionRepository.findTransactionByOrderAndStatus(orderId,status);
    }
}
