package com.nuevospa.taskmanager.service;

import java.util.List;
import com.nuevospa.taskmanager.dto.TaskCreateDTO;
import com.nuevospa.taskmanager.dto.TaskDTO;
import com.nuevospa.taskmanager.dto.TaskPartialUpdateDTO;
import com.nuevospa.taskmanager.dto.TaskUpdateDTO;

public interface TaskService {
    TaskDTO create(TaskCreateDTO dto);
    List<TaskDTO> getAll();
    TaskDTO getById(Long id);
    TaskDTO update(Long id, TaskUpdateDTO dto);
    TaskDTO partialUpdate(Long id, TaskPartialUpdateDTO dto);
    void delete(Long id);
}
