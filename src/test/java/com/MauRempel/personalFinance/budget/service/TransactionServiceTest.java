package com.MauRempel.personalFinance.budget.service;

import com.MauRempel.personalFinance.budget.dto.TransactionRequestDTO;
import com.MauRempel.personalFinance.budget.dto.TransactionResponseDTO;
import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import com.MauRempel.personalFinance.budget.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository);

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2026, 4, 29, 10, 0);
    private static final Pageable PAGEABLE = PageRequest.of(0,10);


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

        when(repository.findAll(any(Specification.class), eq(PAGEABLE))).thenReturn(new PageImpl<>(List.of(transaction), PAGEABLE, 1));

        Page<TransactionResponseDTO> result = service.findAll(null, null, null, null, PAGEABLE);

        assertEquals(1, result.getTotalElements());

        verify(repository).findAll(any(Specification.class),any(Pageable.class));

    }

    @Test
    void shouldFilterOneCategory(){
        Transaction transaction = createTransaction(new BigDecimal("100.00"), TransactionType.INCOME, Category.SALARY);

        when(repository.findAll(any(Specification.class), eq(PAGEABLE))).thenReturn(new PageImpl<>(List.of(transaction), PAGEABLE, 1));

        Page<TransactionResponseDTO> result = service.findAll(Category.SALARY, null, null, null, PAGEABLE);

        assertEquals(1, result.getTotalElements());
        assertEquals(Category.SALARY, result.getContent().getFirst().getCategory());
        assertEquals(TransactionType.INCOME, result.getContent().getFirst().getType());
        verify(repository).findAll(any(Specification.class),any(Pageable.class));

    }

    @Test
    void shouldThrowWhenStartIsAfterEnd(){
        LocalDateTime start = LocalDateTime.of(2026, 5, 31, 23, 59);
        LocalDateTime end = LocalDateTime.of(2026, 5, 1, 0, 0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.findAll(null, null, start, end, PAGEABLE)
        );

        assertEquals("Start timestamp must be before or equal to end timestamp", exception.getMessage());
        verify(repository, never()).findAll(any(Specification.class), any(Pageable.class));
    }



    private Transaction createTransaction(BigDecimal amount, TransactionType type, Category category){
       return new Transaction(amount, type, category, FIXED_TIMESTAMP, null);
    }



}
