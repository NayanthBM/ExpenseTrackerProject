package com.ExpenseTrackerProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
@OpenAPIDefinition(info = @Info(title = "Expense Tracker APIs", version = "1.0", description = "Rest APIs for Tracking expenses"))
public class ExpenseTrackerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerProjectApplication.class, args);
		log.info("Swagger UI: "+"http://localhost:8080/swagger-ui/index.html");
		log.info("H2 Database: "+"http://localhost:8080/h2-console");
	}
}
