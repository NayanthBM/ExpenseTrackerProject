package com.ExpenseTrackerProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Expense Tracker APIs", version = "1.0", description = "Rest APIs for Tracking expenses"))
public class ExpenseTrackerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerProjectApplication.class, args);
		System.out.println("Swagger: "+"http://localhost:8080/swagger-ui/index.html");
		System.out.println("H2 Database: "+"http://localhost:8080/h2-console");
	}
}
