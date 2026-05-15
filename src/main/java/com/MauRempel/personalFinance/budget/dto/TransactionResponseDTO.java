package com.MauRempel.personalFinance.budget.dto;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    @Schema(description = "Transaction identifier", example = "1")
    private final Long id;
    @Schema(description = "Transaction amount", example = "1000.00")
    private final BigDecimal amount;
    @Schema(description = "Transaction type", example = "INCOME")
    private final TransactionType type;
    @Schema(description = "Transaction category", example = "SALARY")
    private final Category category;
    @Schema(description = "Transaction timestamp", example = "2026-05-09T09:00:00")
    private final LocalDateTime timestamp;
    @Schema(description = "Optional transaction description", example = "Monthly salary")
    private final String description;

    public TransactionResponseDTO(Long id, BigDecimal amount, TransactionType type, Category category, LocalDateTime timestamp, String description) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.timestamp = timestamp;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
