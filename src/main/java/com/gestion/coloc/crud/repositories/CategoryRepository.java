package com.gestion.coloc.crud.repositories;


import com.gestion.coloc.crud.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.flatShareCate.idFlat = :flatShareId")
    List<Category> findCategoriesByFlatShareId(@Param("flatShareId") Long flatShareId);

    Category findByName(String nameCat);
}
