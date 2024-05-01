package com.gestion.coloc.crud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
@Entity
public class Expense {
    @Id
    @GeneratedValue
    private Long idExp;
    private String descriptionE;
    private double amount;
    private String receipt;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name="flatShare_id")
    private FlatShare flatShareExpenses;

    @JsonIgnore
    public Category getCategory() {
        return category;
    }

    @JsonIgnore
    public FlatShare getFlatShareExpenses() {
        return flatShareExpenses;
    }
}
