package com.MauRempel.personalFinance.budget.repository;

import com.MauRempel.personalFinance.budget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

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

}
