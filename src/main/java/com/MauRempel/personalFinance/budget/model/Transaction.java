package com.MauRempel.personalFinance.budget.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    protected Transaction(){

    }

    public Transaction(BigDecimal amount, TransactionType type, Category category, LocalDateTime timestamp) {
        validatePositiveAmount(amount);
        validateType(type);
        validateCategory(category);
        validateTimestamp(timestamp);
        this.amount = normalizeAmount(amount);
        this.type = type;
        this.category = category;
        this.timestamp = timestamp;
    }

    public BigDecimal applyTo(BigDecimal currentBalance){
        if(currentBalance == null){
            throw new IllegalArgumentException("Current balance must not be null");
        }
        return type.apply(currentBalance, amount);
    }


    public Long getId() {
        return id;
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

    private void validateTimestamp(LocalDateTime timestamp){
        if(timestamp == null){
            throw new IllegalArgumentException("Timestamp must not be null");
        }
    }
    private BigDecimal normalizeAmount(BigDecimal amount){
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
