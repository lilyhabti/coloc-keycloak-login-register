package com.gestion.coloc.crud.services.imp;

import com.gestion.coloc.crud.models.Category;
import com.gestion.coloc.crud.models.Expense;
import com.gestion.coloc.crud.models.FlatShare;
import com.gestion.coloc.crud.models.User;
import com.gestion.coloc.crud.repositories.CategoryRepository;
import com.gestion.coloc.crud.repositories.ExpenseRepository;
import com.gestion.coloc.crud.repositories.FlatShareRepository;
import com.gestion.coloc.crud.repositories.UserRepository;
import com.gestion.coloc.crud.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImp implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final FlatShareRepository flatShareRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseServiceImp(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, FlatShareRepository flatShareRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.flatShareRepository = flatShareRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Expense createExpense(Expense expense, String nameCat, Long idFlat) {
        Category category = categoryRepository.findByName(nameCat);
        FlatShare flatShare = flatShareRepository.findById(idFlat).orElse(null);


        expense.setCategory(category);
        expense.setFlatShareExpenses(flatShare);

        category.getExpenses().add(expense);
        assert flatShare != null;
        flatShare.getExpenses().add(expense);

        expenseRepository.save(expense);

        return expense;
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    @Override
    public List<Expense> getAllExpenses(String username) {

        User user = userRepository.findByUsername(username);
        FlatShare flatShare = user.getFlatShareColocs();
        return expenseRepository.findExpensesByFlatShareId(flatShare.getIdFlat());
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setDescriptionE(expense.getDescriptionE());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setReceipt(expense.getReceipt());
        existingExpense.setCategory(expense.getCategory());
        return expenseRepository.save(existingExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }
}
