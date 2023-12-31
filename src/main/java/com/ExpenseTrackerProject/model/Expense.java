package com.ExpenseTrackerProject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    @Schema(description = "Name of the expense", example = "Groceries")
    private String name;
    @Schema(description = "Description of the expense", example = "400.00")
    private Double price;
    @Schema(description = "Purchase date of the expense", example = "25/07/2023")
    private LocalDate purchaseDate;
    @OneToOne
    private Category category;
}
