package com.MauRempel.personalFinance.budget.dto;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private final Long id;
    private final BigDecimal amount;
    private final TransactionType type;
    private final Category category;
    private final LocalDateTime timestamp;

    public TransactionResponseDTO(Long id, BigDecimal amount, TransactionType type, Category category, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.timestamp = timestamp;
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


}
