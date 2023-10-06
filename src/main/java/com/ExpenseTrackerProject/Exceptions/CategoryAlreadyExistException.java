package com.ExpenseTrackerProject.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryAlreadyExistException extends Exception{
    public CategoryAlreadyExistException(String message) {
        super(message);
    }

}
