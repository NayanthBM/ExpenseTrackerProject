package com.ExpenseTrackerProject.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.List;

@Component
@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
public class Category {
	
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "category_name", nullable = false)
    @Schema(description = "Name of the category", example = "Shopping")
	private String name;

    @Column(name = "category_description", nullable = false)
    @Schema(description = "Description of the category", example = "Shopping of groceries")
	private String description;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Expense> expenseList;

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
