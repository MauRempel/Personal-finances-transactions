package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public BigDecimal calculateBalance(){

        BigDecimal balance = BigDecimal.ZERO;

        for(Transaction transaction : transactions){
            balance = transaction.applyTo(balance);
        }
        return balance;
    }

    public void addTransaction(TransactionRequestDTO requestDTO){
        if(requestDTO == null){
            throw new IllegalArgumentException("Transaction request must not be null");
        }

        LocalDateTime timestamp = requestDTO.getTimestamp() != null
                ? requestDTO.getTimestamp()
                : LocalDateTime.now().withNano(0);

        Transaction transaction = new Transaction(
                requestDTO.getAmount(),
                requestDTO.getType(),
                requestDTO.getCategory(),
                timestamp
        );

        transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }


}

