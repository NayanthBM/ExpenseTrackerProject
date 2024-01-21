package com.ExpenseTrackerProject.dao;

import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findByNameIgnoreCase(String name);
    Expense deleteByNameIgnoreCase(String name);
}
