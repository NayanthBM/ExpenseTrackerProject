package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/exp/")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getExpense() {
        List<Expense> expenseList = expenseService.getExpense();
        return ResponseEntity.status(HttpStatus.OK).body(expenseList);
    }
    @PutMapping("expenses")
    public ResponseEntity<String> createExpense(Expense expense) {
        try {
            expenseService.createExpense(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created an Expense of ID: "+ expense.getExpenseId());
        }
        catch (ExpenseAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Expense not created");
        }
    }


}
