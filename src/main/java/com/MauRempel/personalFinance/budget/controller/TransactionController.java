package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import com.MauRempel.personalFinance.budget.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all transactions", description = "Returns all the transactions stored on the database")
    @GetMapping
    public List<TransactionResponseDTO> getAll(
            @RequestParam(required = false)Category category,
            @RequestParam(required = false)TransactionType type){
        return service.findAll(category, type);
    }

    @Operation(summary = "Get transaction by ID", description ="Returns a transaction based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/{id}")
    public TransactionResponseDTO getById(@PathVariable Long id){
        return service.findById(id);
    }

    @Operation(summary = "Get the current balance")
    @GetMapping("/balance")
    public BigDecimal getBalance(){
        return service.calculateBalance();
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO add(@Valid @RequestBody TransactionRequestDTO requestDTO){

        return service.addTransaction(requestDTO);

    }
    @Operation(summary = "Delete transaction by ID", description ="Deletes a transaction based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

    @Operation(summary = "Updates an existing transaction by its ID. All fields must be provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @PutMapping("/{id}")
    public TransactionResponseDTO update(@PathVariable Long id, @Valid @RequestBody TransactionRequestDTO requestDTO){
        return service.updateTransaction(id, requestDTO);
    }


}
