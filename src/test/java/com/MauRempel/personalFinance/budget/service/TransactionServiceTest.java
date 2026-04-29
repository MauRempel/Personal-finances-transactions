package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository);

    @Test
    void shouldReturnZeroWhenThereAreNoTransactions(){

        //Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(BigDecimal.ZERO, result);


    }

    @Test
    void shouldAddIncomeTransactionToBalance(){
        BigDecimal value = new BigDecimal("100.00");
        Transaction transaction = new Transaction(value, TransactionType.INCOME, Category.ENTERTAINMENT, LocalDateTime.of(2026, 4, 29, 10, 0));

        //Arrange
        when(repository.findAll()).thenReturn(List.of(transaction));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(value, result);
    }

    @Test
    void shouldSubtractExpenseTransactionFromBalance(){
        BigDecimal value = new BigDecimal("100.00");
        Transaction transaction = new Transaction(value, TransactionType.EXPENSE, Category.ENTERTAINMENT, LocalDateTime.of(2026, 4, 29, 10, 0));

        //Arrange
        when(repository.findAll()).thenReturn(List.of(transaction));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(new BigDecimal("-100.00"), result);
    }

    @Test
    void shouldReturnCorrectBalanceForMixedTransactions(){
        Transaction transaction1 = new Transaction(new BigDecimal("1000.00"), TransactionType.INCOME, Category.SALARY, LocalDateTime.of(2026, 4, 29, 10, 0));
        Transaction transaction2 = new Transaction(new BigDecimal("250.00"), TransactionType.EXPENSE, Category.FOOD, LocalDateTime.of(2026, 4, 29, 10, 0));
        Transaction transaction3 = new Transaction(new BigDecimal("49.90"), TransactionType.EXPENSE, Category.ENTERTAINMENT, LocalDateTime.of(2026, 4, 29, 10, 0));


        //Arrange
        when(repository.findAll()).thenReturn(List.of(transaction1, transaction2, transaction3));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(new BigDecimal("700.10"), result);
    }

    @Test
    void addTransactionShouldSaveTransactionBuiltFromRequestDTO(){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        //Arrange
        transactionRequestDTO.setAmount(new BigDecimal("150.00"));
        transactionRequestDTO.setType(TransactionType.INCOME);
        transactionRequestDTO.setCategory(Category.SALARY);
        transactionRequestDTO.setTimestamp(LocalDateTime.of(2026, 4, 29, 10, 0));

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
}
