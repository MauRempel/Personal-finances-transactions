package com.MauRempel.personalFinance.budget.dto;

public class ErrorResponseDTO {

    private final int status;
    private final String message;
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
