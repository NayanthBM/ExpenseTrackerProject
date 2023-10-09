package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.Exceptions.ExpenseNotFoundException;
import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.dao.ExpenseRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.request.ExpenseCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Expense> getExpense() {
        List<Expense> expenseList = new ArrayList<>();
        Iterable<Expense> iterable = expenseRepository.findAll();
        iterable.forEach(expenseList::add);
        return expenseList;
    }
    public Expense getExpenseByName(String name) throws ExpenseNotFoundException {
        Expense expenseName = expenseRepository.findByName(name);
        if(expenseName != null) {
            return expenseRepository.findByName(name);
        } else {
            throw new ExpenseNotFoundException(notFound);
        }
    }
    public Expense createExpense(ExpenseCreateRequest request) throws Exception{
        Expense expense = new Expense();
        Expense expenseName = expenseRepository.findByName(request.getName());
        if(expenseName!= null) {
            throw new ExpenseAlreadyExistException(alreadyExist);
        }
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if(category.isEmpty()) {
            throw new Exception("Category "+notFound);
        }
        Category categoryToMap = category.get();
        expense.setName(request.getName());
        expense.setPrice(request.getPrice());
        expense.setPurchaseDate(request.getPurchaseDate());
        expense.setCategory(categoryToMap);
        return expenseRepository.save(expense);
    }

    public void updateExpense(Long expenseId, ExpenseCreateRequest request) throws Exception{
        Optional<Expense> existingExpenseId = expenseRepository.findById(expenseId);
        if(existingExpenseId.isPresent()) {
            Expense updateExpense = existingExpenseId.get();
            Optional<Category> category = categoryRepository.findById(request.getCategoryId());
            if(category.isEmpty()) {
                throw new Exception("Category "+notFound);
            }
            Category categoryToMap = category.get();
            updateExpense.setName(request.getName());
            updateExpense.setPrice(request.getPrice());
            updateExpense.setPurchaseDate(request.getPurchaseDate());
            updateExpense.setCategory(categoryToMap);
            expenseRepository.save(updateExpense);
        }
        else {
            throw new Exception(notFound);
        }
    }
    public void deleteExpense(Long expId) throws Exception{
        Optional<Expense> expenseId = expenseRepository.findById(expId);
        if(expenseId.isPresent()) {
            expenseRepository.deleteById(expId);
        }
        else {
            throw new Exception(notFound);
        }
    }
}
