package com.ExpenseTrackerProject.dao;

import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByNameIgnoreCase(String name);
    Category deleteByNameIgnoreCase(String name);
}