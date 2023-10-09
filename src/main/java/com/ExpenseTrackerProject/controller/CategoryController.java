package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.CategoryAlreadyExistException;
import com.ExpenseTrackerProject.Exceptions.CategoryNotFoundException;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;


@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private Category category;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> validationHandler(MethodArgumentNotValidException validation) {
		Map<String, String> errors = new HashMap<>();
		validation.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@GetMapping("/categories")
	@Operation(description = "Get all the categories")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = Category.class)))})
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categoriesList = categoryService.getCategories();
		return ResponseEntity.status(HttpStatus.OK).body(categoriesList);
	}
	@GetMapping("/categories/{categoryName}")
	@Operation(description = "Get a specific Category")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(
			mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Category.class)))})

	public ResponseEntity<Object> getCategoryByName(@Valid @PathVariable("categoryName")String categoryName) {
		try{
			Category categoryByName = categoryService.getCategoryByName(categoryName);
			return ResponseEntity.status(HttpStatus.FOUND).body(categoryByName);
		}
		catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/categories")
	@Operation(description = "Create a new Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CategoryAlreadyExistException.class))),
			@ApiResponse(responseCode = "201", content=@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
		try {
			categoryService.createCategory(request);
		} catch (CategoryAlreadyExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Category "+successCreated);
	}

	@PutMapping("/categories/{categoryID}")
	@Operation(description = "Update a Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Exception.class))),
			@ApiResponse(responseCode = "200", content=@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryCreateRequest request, @PathVariable("categoryID") Long categoryId) {
		try {
			categoryService.updateCategory(categoryId, request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoryId +" "+ successUpdated);
	}

	@DeleteMapping("/categories/{categoryID}")
	@Operation(description = "Delete a Category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = Exception.class))),
			@ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = String.class)))
	})
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryID") Long categoryId) {
		try {
			categoryService.deleteCategory(categoryId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoryId+" "+successDeleted);
	}
}
