package com.ExpenseTrackerProject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    @Schema(description = "Name of the expense", example = "Groceries")
    private String name;
    @Schema(description = "Description of the expense", example = "400.00")
    private Double price;
    @Schema(description = "Purchase date of the expense", example = "2023/10/07")
    private LocalDate purchaseDate;
    @ManyToOne
    private Category category;
}
