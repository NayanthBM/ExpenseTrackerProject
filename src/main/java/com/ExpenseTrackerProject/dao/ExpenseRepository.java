package com.ExpenseTrackerProject.dao;

import com.ExpenseTrackerProject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByName(String name);
}
