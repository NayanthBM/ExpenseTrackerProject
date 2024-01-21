package com.ExpenseTrackerProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
@Component
@Entity
@Data
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    @Schema(description = "Name of the expense", example = "Groceries")
    private String name;
    @Schema(description = "Description of the expense", example = "400.00")
    private Double price;
    @Schema(description = "Purchase date of the expense", example = "25/07/2023")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public Expense(String name, Double price, LocalDate purchaseDate, Category category) {
        this.name = name;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.category = category;
    }
}
