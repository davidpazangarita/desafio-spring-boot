package com.nuevospa.taskmanager.controller;
import com.nuevospa.taskmanager.dto.TaskCreateDTO;
import com.nuevospa.taskmanager.dto.TaskPartialUpdateDTO;
import com.nuevospa.taskmanager.dto.TaskUpdateDTO;
import com.nuevospa.taskmanager.dto.TaskDTO;
import com.nuevospa.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskCreateDTO dto) {
        TaskDTO created = taskService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateDTO dto) {
        return ResponseEntity.ok(taskService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> partialUpdateTask(@PathVariable Long id, @RequestBody TaskPartialUpdateDTO dto) {
        return ResponseEntity.ok(taskService.partialUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}