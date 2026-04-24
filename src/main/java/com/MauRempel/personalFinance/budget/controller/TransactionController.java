package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.service.TransactionService;
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

    @GetMapping
    public List<Transaction> getAll(){
        return service.findAll();
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(){
        return service.calculateBalance();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody TransactionRequestDTO requestDTO){

        service.addTransaction(requestDTO);

    }


}
