package com.gestion.coloc.crud.services;



import com.gestion.coloc.crud.models.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String username, Long idFlat);
    Task getTaskById(Long id);
    List<Task> getAllTasks(Long flatShareId);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
