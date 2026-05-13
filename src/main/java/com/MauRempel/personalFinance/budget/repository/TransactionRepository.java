package com.MauRempel.personalFinance.budget.repository;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        select coalesce(sum(
            case
                when t.type = com.MauRempel.personalFinance.budget.model.TransactionType.INCOME then t.amount
                else -t.amount
            end
        ), 0)
        from Transaction t
        """)
    BigDecimal calculateBalance();

    List<Transaction> findByCategory(Category category);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByCategoryAndType(Category category, TransactionType type);

}
