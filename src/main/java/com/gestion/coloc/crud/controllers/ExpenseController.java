package com.gestion.coloc.crud.controllers;


import com.gestion.coloc.crud.models.Expense;
import com.gestion.coloc.crud.services.imp.ExpenseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {


    private final ExpenseServiceImp expenseService;
    @Autowired
    public ExpenseController(ExpenseServiceImp expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam(name = "flatShareId") Long flatShareId) {
        return ResponseEntity.ok(expenseService.getAllExpenses(flatShareId));
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @RequestParam("nameCat") String nameCat, @RequestParam("idFlat") Long idFlat) {
        Expense createdExpense = expenseService.createExpense(expense, nameCat, idFlat);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}