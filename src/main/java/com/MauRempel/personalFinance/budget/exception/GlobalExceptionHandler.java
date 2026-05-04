package com.MauRempel.personalFinance.budget.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.MauRempel.personalFinance.budget.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex){

        log.warn("Bad Request: {}", ex.getMessage(), ex);

        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex){

        log.warn("Resource not found: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {

        log.error("Unhandled exception while processing the request", ex);

        return buildErrorResponse(500, "Internal Server Error");
    }


    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(int status, String message){
        ErrorResponseDTO error = new ErrorResponseDTO(
                status,
                message,
                OffsetDateTime.now().toString()
        );
        return ResponseEntity.status(status).body(error);
    }

}
