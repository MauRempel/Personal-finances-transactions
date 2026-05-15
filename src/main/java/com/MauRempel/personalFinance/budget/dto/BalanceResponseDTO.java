package com.MauRempel.personalFinance.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class BalanceResponseDTO {

    @Schema(description = "Current calculated balance", example = "849.50")
    private final BigDecimal balance;

    public BalanceResponseDTO(BigDecimal balance){
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
