package com.MauRempel.personalFinance.budget.repository;


import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;


    @Test
    void shouldReturnZeroWhenThereAreNoTransactions(){
        BigDecimal result = repository.calculateBalance();

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldAddToBalanceWhenIncome(){
        repository.save(new Transaction(
                new BigDecimal("1000.00"),
                TransactionType.INCOME,
                Category.SALARY,
                LocalDateTime.of(2026, 5, 10, 9, 0 ),
                "Salary"
        ));

        BigDecimal result = repository.calculateBalance();

        assertEquals(new BigDecimal("1000.00"), result);
    }

    @Test
    void shouldSubtractFromBalanceWhenExpense(){
        repository.save(new Transaction(
                new BigDecimal("100.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 10, 9, 0 ),
                "Lunch"
        ));

        BigDecimal result = repository.calculateBalance();

        assertEquals(new BigDecimal("-100.00"), result);
    }

    @Test
    void shouldReturnCorrectBalanceForMixedTransactions(){
        repository.save(new Transaction(
                new BigDecimal("1000.00"),
                TransactionType.INCOME,
                Category.SALARY,
                LocalDateTime.of(2026, 5, 10, 9, 0 ),
                "Salary"
        ));
        repository.save(new Transaction(
                new BigDecimal("150.50"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 10, 12, 0 ),
                "Lunch"
        ));

        BigDecimal result = repository.calculateBalance();

        assertEquals(new BigDecimal("849.50"), result);
    }
}
