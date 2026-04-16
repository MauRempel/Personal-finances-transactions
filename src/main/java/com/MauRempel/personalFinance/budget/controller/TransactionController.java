package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.DTO.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.service.TransactionService;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void add(@RequestBody TransactionRequestDTO requestDTO){
        Transaction transaction = new Transaction(
                requestDTO.getAmount(),
                requestDTO.getType(),
                requestDTO.getCategory(),
                requestDTO.getTimestamp()
        );
        service.addTransaction(transaction);

    }


}
