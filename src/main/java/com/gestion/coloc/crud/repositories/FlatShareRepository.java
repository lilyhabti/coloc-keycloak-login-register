package com.gestion.coloc.crud.repositories;


import com.gestion.coloc.crud.models.FlatShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlatShareRepository extends JpaRepository<FlatShare, Long> {
    @Query("SELECT f FROM FlatShare f WHERE f.numberOfRoomsOccupied < f.numberOfRooms")
    List<FlatShare> findAllAvailableFlatShares();
}
