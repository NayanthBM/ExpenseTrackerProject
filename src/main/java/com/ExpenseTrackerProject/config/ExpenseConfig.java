package com.ExpenseTrackerProject.config;

import com.ExpenseTrackerProject.dao.ExpenseRepository;
import com.ExpenseTrackerProject.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ExpenseConfig implements CommandLineRunner {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Override
    public void run(String... args) throws Exception {
        Expense expense = new Expense(1L, 1L, "Movie", 400.00, LocalDate.now());
        expenseRepository.save(expense);
    }
}
