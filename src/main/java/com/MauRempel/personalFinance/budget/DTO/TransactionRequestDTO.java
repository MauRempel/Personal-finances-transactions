package com.MauRempel.personalFinance.budget.DTO;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestDTO {

    private BigDecimal amount;
    private TransactionType type;
    private Category category;
    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
