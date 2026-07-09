package com.MauRempel.personalFinance.budget.repository;


import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    @Test
    void shouldFilterTransactionsByCategory(){
        repository.save(transaction(
                new BigDecimal("100.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 10, 12, 0),
                "Lunch"
        ));

        repository.save(transaction(
                new BigDecimal("50.00"),
                TransactionType.EXPENSE,
                Category.TRANSPORT,
                LocalDateTime.of(2026, 5, 10, 18, 0),
                "Bus"
        ));

        Specification<Transaction> specification =
                TransactionSpecification.hasCategory(Category.FOOD);

        List<Transaction> result = repository.findAll(specification);

        assertEquals(1, result.size());
        assertEquals(Category.FOOD, result.getFirst().getCategory());


    }

    @Test
    void shouldFilterTransactionsByType(){
        repository.save(transaction(
                new BigDecimal("1000.00"),
                TransactionType.INCOME,
                Category.SALARY,
                LocalDateTime.of(2026, 5, 10, 9, 0),
                "Salary"
        ));

        repository.save(transaction(
                new BigDecimal("100.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 10, 12, 0),
                "Lunch"
        ));

        Specification<Transaction> specification =
                TransactionSpecification.hasType(TransactionType.EXPENSE);

        List<Transaction> result = repository.findAll(specification);

        assertEquals(1, result.size());
        assertEquals(TransactionType.EXPENSE, result.getFirst().getType());


    }

    @Test
    void shouldFilterTransactionsByTimestampRange(){
        repository.save(transaction(
                new BigDecimal("100.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 1, 12, 0),
                "Old Lunch"
        ));

        repository.save(transaction(
                new BigDecimal("200.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 15, 12, 0),
                "Middle Lunch"
        ));

        repository.save(transaction(
                new BigDecimal("300.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 6, 1, 12, 0),
                "Future Lunch"
        ));

        LocalDateTime start = LocalDateTime.of(2026, 5, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026,5, 31, 23,59 );

        Specification<Transaction> specification = Specification
                .where(TransactionSpecification.timestampGreaterThanOrEqualTo(start))
                .and(TransactionSpecification.timestampLessThanOrEqualTo(end));

        List<Transaction> result = repository.findAll(specification);

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("200.00"), result.getFirst().getAmount());


    }

    @Test
    void shouldFilterTransactionsByCategoryTypeAndTimestampRange() {
        repository.save(transaction(
                new BigDecimal("1000.00"),
                TransactionType.INCOME,
                Category.SALARY,
                LocalDateTime.of(2026, 5, 10, 9, 0),
                "Salary"
        ));

        repository.save(transaction(
                new BigDecimal("100.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 5, 15, 12, 0),
                "Lunch"
        ));

        repository.save(transaction(
                new BigDecimal("80.00"),
                TransactionType.EXPENSE,
                Category.FOOD,
                LocalDateTime.of(2026, 6, 10, 12, 0),
                "June lunch"
        ));
        LocalDateTime start = LocalDateTime.of(2026, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 31, 23, 59);

        Specification<Transaction> specification = Specification
                .where(TransactionSpecification.hasCategory(Category.FOOD))
                .and(TransactionSpecification.hasType(TransactionType.EXPENSE))
                .and(TransactionSpecification.timestampGreaterThanOrEqualTo(start))
                .and(TransactionSpecification.timestampLessThanOrEqualTo(end));

        List<Transaction> result = repository.findAll(specification);

        assertEquals(1, result.size());
        assertEquals("Lunch", result.getFirst().getDescription());
    }

    private Transaction transaction(
            BigDecimal amount,
            TransactionType type,
            Category category,
            LocalDateTime timestamp,
            String description
    ){
        return new Transaction(amount, type, category, timestamp, description);
    }
}
