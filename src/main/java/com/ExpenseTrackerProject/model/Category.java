package com.ExpenseTrackerProject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Category")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class Category {
	
	@Id
    @Column(name = "category_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "category_name")
    @Schema(description = "Name of the category", example = "Shopping")
	private String name;

    @Column(name = "category_description")
    @Schema(description = "Description of the category", example = "Shopping of groceries")
	private String description;	
}
