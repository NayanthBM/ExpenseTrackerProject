package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.request.CategoryCreateRequest;
import com.ExpenseTrackerProject.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;


	@GetMapping("/categories")
	@Operation(description = "Get all the categories")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = Category.class)))})
	public List<Category> getCategories() {
		return categoryService.getCategories();
	}

	@PostMapping("/categories")
	public ResponseEntity<String> saveCategory(@Valid @RequestBody  CategoryCreateRequest request) {
		categoryService.saveCategory(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category Saved");
	}

	@PutMapping("/categories/{categoryID}")
	public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable("categoryID") String catId) {
		try {
			category.setId(Long.parseLong(catId));
			categoryService.updateCategory(category);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok(catId + " Updated Category");
	}

	@DeleteMapping("/categories/{categoryID}")
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryID") String catId) {
		try {
			categoryService.deleteCategory(Long.valueOf(catId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(catId+" ID Deleted Successfully");
	}
}
