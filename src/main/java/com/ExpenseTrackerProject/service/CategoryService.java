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

	@Autowired
	private CategoryRepository categoryRepository;
    @Autowired
    private Category category;

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public void createCategory(CategoryCreateRequest request) throws CategoryAlreadyExistException{
	Category categoryName = categoryRepository.findByName(request.getName());
	if(categoryName != null) {
		throw new CategoryAlreadyExistException(alreadyExist);
	}
    category.setName(request.getName());
    category.setDescription(request.getDescription());
    categoryRepository.save(category);
	}

	public void updateCategory(CategoryCreateRequest request) throws Exception{
		Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
		if(optionalCategory.isPresent()) {
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			categoryRepository.save(category);
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
