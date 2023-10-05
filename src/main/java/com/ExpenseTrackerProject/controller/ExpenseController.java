package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

import static com.ExpenseTrackerProject.constants.ExpensetrackerConstants.*;

@RestController
@RequestMapping("api/v1")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @GetMapping("/expenses")
    @Operation(description = "Get Expense")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Expense.class))))
    public ResponseEntity<List<Expense>> getExpense() {
        List<Expense> expenseList = expenseService.getExpense();
        return ResponseEntity.status(HttpStatus.OK).body(expenseList);
    }
    @PostMapping("/expenses")
    @Operation(description = "Create an Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpenseAlreadyExistException.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> createExpense(@RequestBody Expense expense) {
        try {
            expenseService.createExpense(expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(successCreated+" an Expense of ID: "+ expense.getExpenseId());
        }
        catch (ExpenseAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notCreated);
        }
    }
    @PutMapping("/expenses/{expId}")
    public ResponseEntity<String> updateExpense(@RequestBody Expense expense, @PathVariable("expId") Long expId) {
        try{
            expenseService.updateExpense(expense);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully update an expense of Id"+ expId);
    }
    @DeleteMapping("/expenses/{expId}")
    public ResponseEntity<String> deleteExpense(@PathVariable("expId") Long expId) {
        try{
            expenseService.deleteExpense(expId);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Deleted expense of Id: "+expId);
    }
}
