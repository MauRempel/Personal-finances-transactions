package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.exception.ResourceNotFoundException;
import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import com.MauRempel.personalFinance.budget.repository.TransactionRepository;
import com.MauRempel.personalFinance.budget.repository.TransactionSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public BigDecimal calculateBalance(){
        return transactionRepository.calculateBalance();
    }

    public TransactionResponseDTO addTransaction(TransactionRequestDTO requestDTO){
        if(requestDTO == null){
            throw new IllegalArgumentException("Transaction request must not be null");
        }

        LocalDateTime timestamp = requestDTO.getTimestamp() != null
                ? requestDTO.getTimestamp()
                : LocalDateTime.now().withNano(0);

        Transaction transaction = new Transaction(
                requestDTO.getAmount(),
                requestDTO.getType(),
                requestDTO.getCategory(),
                timestamp,
                requestDTO.getDescription()
        );

        transactionRepository.save(transaction);
        return toResponseDTO(transaction);
    }

    private TransactionResponseDTO toResponseDTO(Transaction transaction){
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getTimestamp(),
                transaction.getDescription()
        );
    }

    public Page<TransactionResponseDTO> findAll(
            Category category,
            TransactionType type,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    ) {


        if(start != null && end != null && start.isAfter(end)){
            throw new IllegalArgumentException("Start timestamp must be before or equal to end timestamp");
        }

        Specification<Transaction> specification = TransactionSpecification.withFilters(
                category,
                type,
                start,
                end
        );


        return transactionRepository.findAll(specification, pageable)
                .map(this::toResponseDTO);
    }

    public TransactionResponseDTO findById(Long id){
        Transaction transaction = findTransactionByIdOrThrow(id);

        return toResponseDTO(transaction);
    }

    public void deleteById(Long id){
        Transaction transaction = findTransactionByIdOrThrow(id);

        transactionRepository.delete(transaction);
    }

    public TransactionResponseDTO updateTransaction(Long id, TransactionRequestDTO requestDTO){
        Transaction transaction = findTransactionByIdOrThrow(id);

        LocalDateTime timestamp = requestDTO.getTimestamp() != null
                ? requestDTO.getTimestamp()
                : transaction.getTimestamp();

        transaction.update(
                requestDTO.getAmount(),
                requestDTO.getType(),
                requestDTO.getCategory(),
                timestamp,
                requestDTO.getDescription()
        );

        transactionRepository.save(transaction);

        return toResponseDTO(transaction);
    }

    private Transaction findTransactionByIdOrThrow(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction with id " + id + " not found"));

    }


}

