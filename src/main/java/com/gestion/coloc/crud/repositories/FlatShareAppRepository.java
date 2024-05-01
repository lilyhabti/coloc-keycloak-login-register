package com.gestion.coloc.crud.repositories;


import com.gestion.coloc.crud.models.FlatShareApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlatShareAppRepository extends JpaRepository<FlatShareApplication, Long> {
    @Query("SELECT fsa FROM FlatShareApplication fsa WHERE fsa.flatShare.idFlat = :idFlat")
    List<FlatShareApplication> findApplicationsByFlatShareId(Long idFlat);
}
