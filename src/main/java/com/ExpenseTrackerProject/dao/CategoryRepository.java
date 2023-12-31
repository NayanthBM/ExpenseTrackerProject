package com.ExpenseTrackerProject.dao;

import com.ExpenseTrackerProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String name);
}