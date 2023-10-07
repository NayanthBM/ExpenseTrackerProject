package com.ExpenseTrackerProject.request;

import com.ExpenseTrackerProject.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCreateRequest {
    @NotNull(message = "Expenses details should not be empty")
    private String name;
    private Double price;
    private LocalDate purchaseDate;
    private long categoryId;
}
