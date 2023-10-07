package com.ExpenseTrackerProject.config;

import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Configuration
public class CategoryConfigurtaion implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void run(String... args) throws Exception {

		Category groceries = new Category(1L, "Groceries", "Shopping Groceries");

		Category  movies = new Category(2L, "Movie", "Watching Movie");

		Category  travel = new Category(3L, "Travel", "Travelling to India");

		categoryRepository.saveAll(Arrays.asList(groceries, movies, travel));
	}
}
