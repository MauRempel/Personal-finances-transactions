package com.MauRempel.personalFinance.budget.model;

import java.math.BigDecimal;

public enum TransactionType {
    INCOME{
        @Override
        public BigDecimal apply(BigDecimal balance, BigDecimal amount){
            return balance.add(amount);
        }
    },
    EXPENSE{
        @Override
        public BigDecimal apply(BigDecimal balance, BigDecimal amount){
            return balance.subtract(amount);
        }
    };

    public abstract BigDecimal apply(BigDecimal balance, BigDecimal amount);
}