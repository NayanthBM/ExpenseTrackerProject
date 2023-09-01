package com.ExpenseTrackerProject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Category")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
	
	@Id
    @Column(name = "category_ID")
	@SequenceGenerator(
			name = "category_sequence",
			sequenceName = "category_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "category_sequence"
    )
    @Schema(description = "Category ID")
	private long id;
	
	@Column(name = "category_name")
    @Schema(description = "Description of the category")
	private String name;

    @Column(name = "category_description")
    @Schema(description = "Name of the category")
	private String description;	
}
