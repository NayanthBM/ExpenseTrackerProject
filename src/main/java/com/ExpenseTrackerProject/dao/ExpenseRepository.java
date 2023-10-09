package com.ExpenseTrackerProject.dao;

import com.ExpenseTrackerProject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByName(String name);
}
