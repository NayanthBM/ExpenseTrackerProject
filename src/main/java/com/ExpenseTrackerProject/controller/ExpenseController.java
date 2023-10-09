package com.ExpenseTrackerProject.controller;

import com.ExpenseTrackerProject.Exceptions.ExpenseAlreadyExistException;
import com.ExpenseTrackerProject.Exceptions.ExpenseNotFoundException;
import com.ExpenseTrackerProject.model.Expense;
import com.ExpenseTrackerProject.request.ExpenseCreateRequest;
import com.ExpenseTrackerProject.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ExpenseTrackerProject.constants.ExpenseTrackerConstants.*;

@RestController
@RequestMapping("api/v1")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> validationHandler(MethodArgumentNotValidException validator) {
        List<String > errors = new ArrayList<>();
        validator.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @GetMapping("/expenses")
    @Operation(description = "Get Expense")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Expense.class))))
    public ResponseEntity<List<Expense>> getExpense() {
        List<Expense> expenseList = expenseService.getExpense();
        return ResponseEntity.status(HttpStatus.OK).body(expenseList);
    }

    @GetMapping("/expenses/{expenseName}")
    @Operation(description = "Get Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Expense.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type= "object")))
    })
    public ResponseEntity<Object> getExpenseByName(@Valid @PathVariable("expenseName") String expenseName) {
        try {
            Expense expenseByName = expenseService.getExpenseByName(expenseName);
            return ResponseEntity.status(HttpStatus.FOUND).body(expenseByName);
        }
        catch (ExpenseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
            Expense expense = expenseService.createExpense(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(successCreated+" an Expense of ID: "+expense.getExpenseId());
        }
        catch (ExpenseAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notCreated);
        }
    }
    @PutMapping("/expenses/{expenseId}")
    @Operation(description = "Update an Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Exception.class))),
            @ApiResponse(responseCode = "202", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> updateExpense(@Valid  @RequestBody ExpenseCreateRequest request, @PathVariable("expenseId") Long expenseId) {
        try{
            expenseService.updateExpense(expenseId, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(successUpdated+" "+expenseId);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/expenses/{expenseId}")
    @Operation(description = "Delete an Expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Exception.class))),
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        try{
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.status(HttpStatus.OK).body(successDeleted+" "+expenseId);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
