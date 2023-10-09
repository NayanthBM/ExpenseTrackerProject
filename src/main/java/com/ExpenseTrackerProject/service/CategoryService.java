package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.Exceptions.CategoryAlreadyExistException;
import com.ExpenseTrackerProject.Exceptions.CategoryNotFoundException;
import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.request.CategoryCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getCategories() {
		List<Category> categoryList = new ArrayList<>();
		Iterable<Category> iterable = categoryRepository.findAll();
		iterable.forEach(categoryList::add);
		return categoryList;
	}
	public Category getCategoryByName(String name) throws CategoryNotFoundException{
		Category categoryName = categoryRepository.findByName(name);
		if(categoryName != null) {
			return categoryName;
		}
		else {
			throw new CategoryNotFoundException(notFound);
		}
	}

	public void createCategory(CategoryCreateRequest request) throws CategoryAlreadyExistException{
	Category category = new Category();
	Category categoryName = categoryRepository.findByName(request.getName());
	if(categoryName != null) {
		throw new CategoryAlreadyExistException(alreadyExist);
	}
    category.setName(request.getName());
    category.setDescription(request.getDescription());
    categoryRepository.save(category);
	}

	public void updateCategory(Long categoryId, CategoryCreateRequest request) throws Exception{
		Optional<Category> existingCategoryId = categoryRepository.findById(categoryId);
		if(existingCategoryId.isPresent()) {
			Category updateCategory = existingCategoryId.get();
			updateCategory.setName(request.getName());
			updateCategory.setDescription(request.getDescription());
			categoryRepository.save(updateCategory);
		} else {
			throw new Exception(notFound);
		}
	}
	public void deleteCategory(Long catID) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(catID);
		if (optionalCategory.isPresent()){
			categoryRepository.deleteById(catID);
		} else {
			throw new Exception(notFound);
		}
	}
}
