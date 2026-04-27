package com.MauRempel.personalFinance.budget.repository;

import com.MauRempel.personalFinance.budget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
