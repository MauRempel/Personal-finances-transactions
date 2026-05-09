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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository);

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2026, 4, 29, 10, 0);

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
        Transaction transaction = createTransaction(value, TransactionType.INCOME, Category.ENTERTAINMENT);

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
        Transaction transaction = createTransaction(value, TransactionType.EXPENSE, Category.ENTERTAINMENT);

        //Arrange
        when(repository.findAll()).thenReturn(List.of(transaction));

        //Act
        BigDecimal result = service.calculateBalance();

        //Assert
        assertEquals(new BigDecimal("-100.00"), result);
    }

    @Test
    void shouldReturnCorrectBalanceForMixedTransactions(){
        Transaction transaction1 = createTransaction(new BigDecimal("1000.00"), TransactionType.INCOME, Category.SALARY);
        Transaction transaction2 = createTransaction(new BigDecimal("250.00"), TransactionType.EXPENSE, Category.FOOD);
        Transaction transaction3 = createTransaction(new BigDecimal("49.90"), TransactionType.EXPENSE, Category.ENTERTAINMENT);


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
    private Transaction createTransaction(BigDecimal amount, TransactionType type, Category category){
        return new Transaction(amount, type, category, FIXED_TIMESTAMP, null);
    }
}
