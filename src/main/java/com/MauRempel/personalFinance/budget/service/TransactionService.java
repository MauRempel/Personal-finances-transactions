package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public BigDecimal calculateBalance(){

        BigDecimal balance = BigDecimal.ZERO;
        if(transactions.isEmpty()){
            return balance;
        }
        for(Transaction t : transactions){
            balance = t.applyTo(balance);
        }
        return balance;
    }

    public void addTransaction(Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("The List must not be null");
        }
        transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }


}

