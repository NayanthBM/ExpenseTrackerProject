package com.ExpenseTrackerProject.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpenseNotFoundException extends Exception{
    public ExpenseNotFoundException(String message) {
        super(message);
    }

}
