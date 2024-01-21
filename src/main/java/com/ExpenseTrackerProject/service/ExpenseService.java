package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.dao.ExpenseRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.request.ExpenseCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private Expense expense;

    public List<Expense> getExpense() {
        List<Expense> expenseList = new ArrayList<>();
        Iterable<Expense> iterable = expenseRepository.findAll();
        iterable.forEach(expenseList::add);
        return expenseList;
    }
    @Autowired
    private CategoryRepository categoryRepository;
    public void createExpense(ExpenseCreateRequest request) throws Exception{
        Optional<Expense> expenseName = expenseRepository.findByNameIgnoreCase(request.getName());
        if(expenseName.isPresent()) {
            throw new ExpenseAlreadyExistException(NO_DUPLICATE_EXPENSES_ALLOWED.getValue());
        }
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if(category.isEmpty()) {
            throw new Exception(NOT_CREATED.getValue());
        }
        Category categoryToMap = category.get();
        expense.setName(request.getName());
        expense.setPrice(request.getPrice());
        expense.setPurchaseDate(LocalDate.now());
        expense.setCategory(categoryToMap);
        expenseRepository.save(expense);
    }

    public void updateExpense(ExpenseCreateRequest request) throws Exception{
        Optional<Expense> expenseId = expenseRepository.findById(expense.getExpenseId());
        if(expenseId.isPresent()) {
            Optional<Category> category = categoryRepository.findById(request.getCategoryId());
            if(category.isEmpty()) {
                throw new Exception(NOT_CREATED.getValue());
            }
            Category categoryToMap = category.get();
            expense.setName(request.getName());
            expense.setPrice(request.getPrice());
            expense.setPurchaseDate(LocalDate.now());
            expense.setCategory(categoryToMap);
            expenseRepository.save(expense);
        }
        else {
            throw new Exception(NOT_FOUND.getValue());
        }
    }
    public void deleteExpense(Integer expId) throws Exception{
        Optional<Expense> expenseId = expenseRepository.findById(expId);
        if(expenseId.isPresent()) {
            expenseRepository.deleteById(expId);
        }
        else {
            throw new Exception(NOT_FOUND.getValue());
        }
    }

    public Expense fidByExpenseName(String expenseName) throws Exception{
        Optional<Expense> expense = expenseRepository.findByNameIgnoreCase(expenseName);
        if(expense.isEmpty()) {
            throw new Exception(NOT_FOUND.getValue());
        }
        return expense.get();
    }
}
