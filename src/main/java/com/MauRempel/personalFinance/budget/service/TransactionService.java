package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public BigDecimal calculateBalance(){

        BigDecimal balance = BigDecimal.ZERO;

        for(Transaction transaction : transactionRepository.findAll()){
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

        transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }


}

