package com.ExpenseTrackerProject.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExpenseAlreadyExistException extends Exception{
    public ExpenseAlreadyExistException(String message) {
        super(message);
    }

}
