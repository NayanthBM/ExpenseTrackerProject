package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.Exceptions.CategoryAlreadyExistException;
import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.request.CategoryCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;
    private Category category;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository, Category category) {
		this.categoryRepository = categoryRepository;
		this.category = category;
	}

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public void createCategory(CategoryCreateRequest request) throws CategoryAlreadyExistException{
	Optional<Category> categoryName = categoryRepository.findByNameIgnoreCase(request.getName());
	if(categoryName.isEmpty()) {
		throw new CategoryAlreadyExistException(NO_DUPLICATE_CATEGORIES_ALLOWED.getValue());
	}
    category.setName(request.getName());
    category.setDescription(request.getDescription());
    categoryRepository.save(categoryName.get());
	}

	public void updateCategory(CategoryCreateRequest request) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
		if(optionalCategory.isPresent()) {
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			categoryRepository.save(category);
		} else {
			throw new Exception(NOT_FOUND.getValue());
		}
	}
	public void deleteCategory(Integer catID) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(catID);
		if (optionalCategory.isPresent()){
			categoryRepository.deleteById(catID);
		} else {
			throw new Exception(NOT_FOUND.getValue());
		}
	}

	public Category findCategoryByName(String categoryName) throws Exception {
		Optional<Category> category = categoryRepository.findByNameIgnoreCase(categoryName);
		if(category.isEmpty()) {
			throw new Exception(NOT_FOUND.getValue());
		}
		return category.get();
	}

	public Category deleteCategoryByName(String categoryName) throws Exception {
		Optional<Category> categoryCheck = categoryRepository.findByNameIgnoreCase(categoryName);
		if(categoryCheck.isEmpty()) {
			throw new Exception(NOT_FOUND.getValue());
		}
        return categoryRepository.deleteByNameIgnoreCase(categoryName);
	}
}
