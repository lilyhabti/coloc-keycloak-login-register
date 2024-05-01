package com.gestion.coloc.crud.services;



import com.gestion.coloc.crud.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, Long flatShareId);
    Category getCategoryById(Long id);
    List<Category> getCategoriesByFlatShareId(Long flatShareId);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
