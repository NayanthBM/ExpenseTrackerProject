package com.ExpenseTrackerProject.service;

import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.request.CategoryCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}
	public void saveCategory(CategoryCreateRequest request) {
		Category saveCategory = new Category();
		saveCategory.setName(request.getName());
		saveCategory.setDescription(request.getDescription());
		categoryRepository.save(saveCategory);
	}

	public void updateCategory(Category category) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
		if(optionalCategory.isPresent()) {
			categoryRepository.save(category);
		} else {
			throw new Exception("Category id not found");
		}
	}
	public void deleteCategory(Long catID) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(catID);
		if (optionalCategory.isPresent()){
			categoryRepository.deleteById(catID);
		} else {
			throw new Exception("Id not found");
		}
	}
}
