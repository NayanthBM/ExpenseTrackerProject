package com.ExpenseTrackerProject.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCreateRequest {
    @NotNull(message = "Name should not be empty")
    private String name;
    @NotNull(message = "Price should not be empty")
    private Double price;
    @NotNull(message = "Purchase Date should not be empty")
    private LocalDate purchaseDate;
    private long categoryId;
}
