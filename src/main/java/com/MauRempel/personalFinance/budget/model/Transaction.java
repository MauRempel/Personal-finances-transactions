package com.MauRempel.personalFinance.budget.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


public class Transaction{

    private final BigDecimal amount;
    private final TransactionType type;
    private final Category category;
    private final LocalDateTime timestamp;


    public Transaction(BigDecimal amount, TransactionType type, Category category, LocalDateTime timestamp) {
        validatePositiveAmount(amount);
        validateType(type);
        validateCategory(category);
        this.amount = normalizeBigDecimal(amount);
        this.type = type;
        this.category = category;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now().withNano(0);
    }

    public BigDecimal applyTo(BigDecimal currentBalance){
        if(currentBalance == null){
            throw new IllegalArgumentException("Current balance must not be null");
        }
        if(type == TransactionType.INCOME){
            return currentBalance.add(amount);
        }
        if(type == TransactionType.EXPENSE){
            return currentBalance.subtract(amount);
        }
        throw new IllegalStateException("Unsupported transaction type " + type);
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private void validatePositiveAmount(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    private void validateType(TransactionType type){
        if(type == null){
            throw new IllegalArgumentException("Transaction type must not be null");
        }
    }
    private void validateCategory(Category category){
        if(category == null){
            throw new IllegalArgumentException("Category must not be null");
        }

    }
    private BigDecimal normalizeBigDecimal(BigDecimal amount){
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
