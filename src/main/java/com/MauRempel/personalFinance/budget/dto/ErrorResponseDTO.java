package com.MauRempel.personalFinance.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponseDTO {

    @Schema(description = "HTTP status code", example = "400")
    private final int status;
    @Schema(description = "Error message", example = "amount: Amount must be greater than zero")
    private final String message;
    @Schema(description = "Timestamp when the error occurred", example = "2026-05-09T10:15:30-03:00")
    private final String timestamp;

    public ErrorResponseDTO(int status, String message, String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
