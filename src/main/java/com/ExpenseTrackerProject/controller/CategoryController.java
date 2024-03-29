package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.CategoryAlreadyExistException;
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

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;


@RestController
@RequestMapping("/exp/v1")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private Category category;

	@GetMapping("/categories")
	@Operation(description = "Get all the categories")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = Category.class)))})
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categoryList = categoryService.getCategories();
		return ResponseEntity.status(HttpStatus.OK).body(categoryList);
	}

	@PostMapping("/categories")
	@Operation(description = "Create a new Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CategoryAlreadyExistException.class))),
			@ApiResponse(responseCode = "201", content=@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> createCategory(@Valid @RequestBody  CategoryCreateRequest request) {
		try {
			categoryService.createCategory(request);
		} catch (CategoryAlreadyExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Category "+SUCCESSFULLY_CREATED.getValue());
	}

	@PutMapping("/categories/{categoryID}")
	@Operation(description = "Update a Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Exception.class))),
			@ApiResponse(responseCode = "200", content=@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryCreateRequest request, @PathVariable("categoryID") Integer categoryId) {
		try {
			category.setId(categoryId);
			categoryService.updateCategory(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoryId +" "+ SUCCESSFULLY_UPDATED.getValue());
	}

	@DeleteMapping("/categories/{categoryID}")
	@Operation(description = "Delete a Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Exception.class))),
			@ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryID") Integer categoryId) {
		try {
			categoryService.deleteCategory(categoryId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoryId+" "+SUCCESSFULLY_DELETED.getValue());
	}

	@GetMapping("/categoryName/{categoryName}")
	@Operation(description = "Get a category using category name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(
			mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Category.class))),
			@ApiResponse(responseCode = "404", content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = Category.class)))
	})
	public ResponseEntity<Category> fetchCategoryByName(@PathVariable("") String categoryName) {
		try {
			Category category = categoryService.findCategoryByName(categoryName);
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@DeleteMapping("/removeByName/{categoryName}")
	@Operation(description = "Delete a Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = Exception.class))),
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<Category> deleteCategoryByName(@PathVariable("categoryName") String categoryName) {
		try {
			Category category = categoryService.deleteCategoryByName(categoryName);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(category);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
