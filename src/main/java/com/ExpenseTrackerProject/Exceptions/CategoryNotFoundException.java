package com.ExpenseTrackerProject.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String message) {
        super(message);
    }

}
