package com.gestion.coloc.crud.repositories;


import com.gestion.coloc.crud.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.flatShareExpenses.idFlat = :idFlat")
    List<Expense> findExpensesByFlatShareId(Long idFlat);
}
