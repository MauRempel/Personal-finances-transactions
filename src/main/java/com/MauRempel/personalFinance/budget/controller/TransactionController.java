package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.dto.BalanceResponseDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import com.MauRempel.personalFinance.budget.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Tag(name = "Transactions", description = "Operations for managing financial transactions")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Operation(
            summary = "List transactions",
            description = "Returns all transactions and supports optional filters by category, type, start timestamp, and end timestamp"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid filter input"
    )
    @GetMapping
    public Page<TransactionResponseDTO> getAll(
            @Parameter(description = "Filter by category", example = "FOOD")
            @RequestParam(required = false)
            Category category,
            @Parameter(description = "Filter by transaction type", example = "EXPENSE")
            @RequestParam(required = false)
            TransactionType type,
            @Parameter(description = "Filter transactions from this timestamp", example = "2026-05-01T00:00:00")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @Parameter(description = "Filter transactions until this timestamp", example = "2026-05-31T23:59:59")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end,
            @PageableDefault(
                    size = 10,
                    sort = "timestamp",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return service.findAll(category, type, start, end, pageable);
    }

    @Operation(summary = "Get transaction by ID", description ="Returns a transaction based on its ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction found"
                    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID supplied"
                    ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction not found"
                    )
    })
    @GetMapping("/{id}")
    public TransactionResponseDTO getById(@PathVariable Long id){
        return service.findById(id);
    }

    @Operation(summary = "Get the current balance", description = "Returns the current balance calculated from income and expense transactions")
    @ApiResponse(
            responseCode = "200",
            description = "Balance returned successfully"
            )
    @GetMapping("/balance")
    public BalanceResponseDTO getBalance(){
        return new BalanceResponseDTO(service.calculateBalance());
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transaction created"
                    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
                    )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO add(@Valid @RequestBody TransactionRequestDTO requestDTO){

        return service.addTransaction(requestDTO);

    }
    @Operation(summary = "Delete transaction by ID", description ="Deletes a transaction based on its ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Transaction deleted"
                    ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction not found"
                    )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

    @Operation(summary = "Updates an existing transaction by its ID. All fields must be provided.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction not found"
            )
    })
    @PutMapping("/{id}")
    public TransactionResponseDTO update(@PathVariable Long id, @Valid @RequestBody TransactionRequestDTO requestDTO){
        return service.updateTransaction(id, requestDTO);
    }


}
