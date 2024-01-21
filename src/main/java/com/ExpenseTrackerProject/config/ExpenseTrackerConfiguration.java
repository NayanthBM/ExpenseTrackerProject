package com.ExpenseTrackerProject.config;

import com.ExpenseTrackerProject.dao.CategoryRepository;
import com.ExpenseTrackerProject.dao.ExpenseRepository;
import com.ExpenseTrackerProject.model.Category;
import com.ExpenseTrackerProject.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class ExpenseTrackerConfiguration implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public void run(String... args) throws Exception {
		Category shopping = new Category("Shopping", "Shopping of groceries");
		Category entertainment = new Category("Entertainment", "Movie tickets, etc.");
		Category electronics = new Category("Electronics", "Gadgets and devices");
		Category travel = new Category("Travel", "Flight tickets, hotels, etc.");
		Category dining = new Category("Dining", "Restaurant bills");

		categoryRepository.saveAll(Arrays.asList(shopping, entertainment, electronics, travel, dining));

		Expense groceries = new Expense("Groceries", 400.00, LocalDate.now(), shopping);
		Expense movie = new Expense("Movie", 20.00, LocalDate.now(), entertainment);
		Expense laptop = new Expense("Laptop", 1200.00, LocalDate.now(), electronics);
		Expense flightTicket = new Expense("Flight Ticket", 300.00, LocalDate.now(), travel);
		Expense dinner = new Expense("Dinner", 50.00, LocalDate.now(), dining);

		expenseRepository.saveAll(Arrays.asList(groceries, movie, laptop, flightTicket, dinner));

	}
}
