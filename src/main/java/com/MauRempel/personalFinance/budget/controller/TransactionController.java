package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Operation(summary = "Get all transactions")
    @GetMapping
    public List<TransactionResponseDTO> getAll(){
        return service.findAll();
    }

    @Operation(summary = "Get the current balance")
    @GetMapping("/balance")
    public BigDecimal getBalance(){
        return service.calculateBalance();
    }

    @Operation(summary = "Create a new transaction")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody TransactionRequestDTO requestDTO){

        service.addTransaction(requestDTO);

    }


}
