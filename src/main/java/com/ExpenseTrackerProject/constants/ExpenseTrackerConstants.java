package com.ExpenseTrackerProject.constants;

import lombok.Getter;

@Getter
public enum ExpenseTrackerConstants {

    SUCCESSFULLY_CREATED("SUCCESSFULLY CREATED"),
    NOT_CREATED("NOT CREATED"),
    SUCCESSFULLY_UPDATED("SUCCESSFULLY UPDATED"),
    NOT_FOUND("NOT FOUND"),
    NO_DUPLICATE_CATEGORIES_ALLOWED("NO DUPLICATE CATEGORIES ARE ALLOWED"),
    NO_DUPLICATE_EXPENSES_ALLOWED("NO DUPLICATE EXPENSES ARE ALLOWED"),
    SUCCESSFULLY_DELETED("SUCCESSFULLY DELETED");

    private final String value;

    ExpenseTrackerConstants(String value) {
        this.value = value;
    }

}
