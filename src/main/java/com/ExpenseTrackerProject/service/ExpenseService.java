package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.dao.ExpenseRepository;
import com.ExpenseTrackerProject.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getExpense() {
       List<Expense> expenseList = new ArrayList<>();
        Iterable<Expense> iterable = expenseRepository.findAll();
        iterable.forEach(expenseList::add);
        return expenseList;
    }

    public void createExpense(Expense expense) throws Exception{
        Optional<Expense> expenseId = expenseRepository.findById(expense.getExpenseId());
        if(expenseId.isPresent()){
            throw new ExpenseAlreadyExistException("Expense Already Exists");
        }
        expenseRepository.save(expense);
        LocalDate date = LocalDate.now();
        expense.setRegisteredDate(date);
    }
}