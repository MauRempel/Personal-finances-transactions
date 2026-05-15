package com.MauRempel.personalFinance.budget.dto;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestDTO {

    @Schema(example = "100.50", description = "Transaction amount in currency format")
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @Schema(description = "Transaction type", example = "EXPENSE")
    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Schema(description = "Transaction category", example = "FOOD")
    @NotNull(message = "Category is required")
    private Category category;

    @Schema(description = "Transaction timestamp in ISO-8601 format", example = "2026-04-16T09:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Optional free-text description", example = "Lunch with coworkers")
    @Size(max = 255, message = "Description must have at most 255 characters")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
