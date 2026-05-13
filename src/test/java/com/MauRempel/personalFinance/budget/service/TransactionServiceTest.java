package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import com.MauRempel.personalFinance.budget.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository);

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2026, 4, 29, 10, 0);

    @Test
    void shouldReturnZeroWhenBalanceIsZero(){

        //Arrange
        when(repository.calculateBalance()).thenReturn(BigDecimal.ZERO);

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(BigDecimal.ZERO, result);


    }

    @Test
    void shouldReturnPositiveBalance(){


        //Arrange
        when(repository.calculateBalance()).thenReturn(new BigDecimal("700.10"));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(new BigDecimal("700.10"), result);
    }

    @Test
    void shouldReturnNegativeBalance(){

        //Arrange
        when(repository.calculateBalance()).thenReturn(new BigDecimal("-100.00"));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(new BigDecimal("-100.00"), result);
    }



    @Test
    void addTransactionShouldSaveTransactionBuiltFromRequestDTO(){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        //Arrange
        transactionRequestDTO.setAmount(new BigDecimal("150.00"));
        transactionRequestDTO.setType(TransactionType.INCOME);
        transactionRequestDTO.setCategory(Category.SALARY);
        transactionRequestDTO.setTimestamp(FIXED_TIMESTAMP);

        //Act
        service.addTransaction(transactionRequestDTO);

        //Assert
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).save(transactionArgumentCaptor.capture());

        Transaction result = transactionArgumentCaptor.getValue();

        assertEquals(new BigDecimal("150.00"), result.getAmount());
        assertEquals(TransactionType.INCOME, result.getType());
        assertEquals(Category.SALARY, result.getCategory());
        assertEquals(LocalDateTime.of(2026, 4, 29, 10, 0), result.getTimestamp());
    }

    @Test
    void addTransactionShouldUseCurrentTimeWhenTimestampIsNull(){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        //Arrange
        transactionRequestDTO.setAmount(new BigDecimal("150.00"));
        transactionRequestDTO.setType(TransactionType.INCOME);
        transactionRequestDTO.setCategory(Category.SALARY);

        //Act
        LocalDateTime before = LocalDateTime.now();
        service.addTransaction(transactionRequestDTO);

        //Assert
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).save(captor.capture());

        LocalDateTime after = LocalDateTime.now();

        Transaction result = captor.getValue();

        assertNotNull(result.getTimestamp());
        assertFalse(result.getTimestamp().isBefore(before.withNano(0)));
        assertFalse(result.getTimestamp().isAfter(after.withNano(0)));

    }

    @Test
    void shouldUseNoFilter(){
        Transaction transaction = createTransaction(new BigDecimal("100.00"), TransactionType.INCOME, Category.SALARY);

        when(repository.findAll()).thenReturn(List.of(transaction));

        List<TransactionResponseDTO> result = service.findAll(null, null);

        assertEquals(1, result.size());
        assertEquals(Category.SALARY, result.getFirst().getCategory());
        assertEquals(TransactionType.INCOME, result.getFirst().getType());
        verify(repository).findAll();
        verify(repository, never()).findByCategory(any());
        verify(repository, never()).findByType(any());
        verify(repository, never()).findByCategoryAndType(any(),any());
    }

    @Test
    void shouldFilterByCategory(){
        Transaction transaction = createTransaction(new BigDecimal("50.00"), TransactionType.EXPENSE, Category.FOOD);

        when(repository.findByCategory(Category.FOOD)).thenReturn(List.of(transaction));

        List<TransactionResponseDTO> result = service.findAll(Category.FOOD, null);

        assertEquals(1, result.size());
        assertEquals(Category.FOOD, result.getFirst().getCategory());
        verify(repository).findByCategory(Category.FOOD);
    }

    @Test
    void shouldFilterByType(){
        Transaction transaction = createTransaction(new BigDecimal("100.00"), TransactionType.EXPENSE, Category.ENTERTAINMENT);

        when(repository.findByType(TransactionType.EXPENSE)).thenReturn(List.of(transaction));

        List<TransactionResponseDTO> result = service.findAll(null, TransactionType.EXPENSE);

        assertEquals(1, result.size());
        assertEquals(TransactionType.EXPENSE, result.getFirst().getType());
        verify(repository).findByType(TransactionType.EXPENSE);
    }
    @Test
    void shouldFilterByCategoryAndType(){
        Transaction transaction = createTransaction(new BigDecimal("100.00"), TransactionType.INCOME, Category.SALARY);

        when(repository.findByCategoryAndType(Category.SALARY, TransactionType.INCOME)).thenReturn(List.of(transaction));

        List<TransactionResponseDTO> result = service.findAll(Category.SALARY, TransactionType.INCOME);

        assertEquals(1, result.size());
        assertEquals(Category.SALARY, result.getFirst().getCategory());
        assertEquals(TransactionType.INCOME, result.getFirst().getType());
        verify(repository).findByCategoryAndType(Category.SALARY, TransactionType.INCOME);
    }

    private Transaction createTransaction(BigDecimal amount, TransactionType type, Category category){
       return new Transaction(amount, type, category, FIXED_TIMESTAMP, null);
    }



}
