package com.MauRempel.personalFinance.budget.repository;

import com.MauRempel.personalFinance.budget.model.Category;
import com.MauRempel.personalFinance.budget.model.Transaction;
import com.MauRempel.personalFinance.budget.model.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public final class TransactionSpecification {

    private TransactionSpecification() {
    }

    public static Specification<Transaction> hasCategory(Category category) {
        return (root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }
    public static Specification<Transaction> hasType(TransactionType type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("type"), type);
    }
    public static Specification<Transaction> timestampGreaterThanOrEqualTo(LocalDateTime start) {
        return (root, query, criteriaBuilder) ->
                start == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), start);
    }
    public static Specification<Transaction> timestampLessThanOrEqualTo(LocalDateTime end) {
        return (root, query, criteriaBuilder) ->
                end == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), end);
    }

    public static Specification<Transaction> withFilters(
            Category category,
            TransactionType type,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return Specification
                .where(hasCategory(category))
                .and(hasType(type))
                .and(timestampGreaterThanOrEqualTo(start))
                .and(timestampLessThanOrEqualTo(end));
    }
}
