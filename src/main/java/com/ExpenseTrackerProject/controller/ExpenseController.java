package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.request.ExpenseCreateRequest;
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


import javax.validation.Valid;
import java.util.List;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@RestController
@RequestMapping("exp/v1")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private Expense expense;
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
            @ApiResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpenseAlreadyExistException.class))),
            @ApiResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> createExpense(@Valid @RequestBody ExpenseCreateRequest request) {
        try {
            expenseService.createExpense(request);
        } catch (ExpenseAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_CREATED.getValue());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFULLY_CREATED.getValue() + " an Expense: " + request.getName());
    }

    @PutMapping("/expenses/{expenseId}")
    @Operation(description = "Update an Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Exception.class))),
            @ApiResponse(responseCode = "202", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> updateExpense(@Valid  @RequestBody ExpenseCreateRequest request, @PathVariable("expenseId") Integer expenseId) {
        try{
            expense.setExpenseId(expenseId);
            expenseService.updateExpense(request);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SUCCESSFULLY_UPDATED.getValue()+" Expense "+request.getName());
    }
    @DeleteMapping("/expenses/{expenseId}")
    @Operation(description = "Delete an Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Exception.class))),
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> deleteExpense(@PathVariable("expenseId") Integer expenseId) {
        try{
            expenseService.deleteExpense(expenseId);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(SUCCESSFULLY_DELETED.getValue()+" "+expenseId);
    }

    @GetMapping("/expenseName/{expenseName}")
    public ResponseEntity<Expense> fetchExpenseByName(@PathVariable("expenseName") String expenseName) {
        try {
            Expense expense = expenseService.fidByExpenseName(expenseName);
            return ResponseEntity.status(HttpStatus.FOUND).body(expense);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
