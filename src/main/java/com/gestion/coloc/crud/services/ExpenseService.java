package com.gestion.coloc.crud.services;


import com.gestion.coloc.crud.models.Expense;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense expense, String nameCat, Long idFlat);
    Expense getExpenseById(Long id);
    List<Expense> getAllExpenses(Long flatShareId);
    Expense updateExpense(Long id, Expense expense);
    void deleteExpense(Long id);
}
