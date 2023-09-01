package com.ExpenseTrackerProject.config;

import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CategoryConfigurtaion implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void run(String... args) throws Exception {

		Category groceries = new Category(1L, "Groceries", "Shopping Groceries");

		Category  movies = new Category(2L, "Movie", "Watching Movie");

		Category  travel = new Category(3L, "Travel", "Travelling to Ooty");

		categoryRepository.saveAll(Arrays.asList(groceries, movies, travel));

		System.out.println("Added Categories Successfully");

	}

}
