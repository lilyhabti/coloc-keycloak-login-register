package com.gestion.coloc.crud.repositories;


import com.gestion.coloc.crud.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.flatShareTasks.idFlat = :idFlat")
    List<Task> findTasksByFlatShareId(Long idFlat);
}
