package com.ExpenseTrackerProject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Description should not be empty")
    private String description;
}
