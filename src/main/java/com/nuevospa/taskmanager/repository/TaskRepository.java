package com.nuevospa.taskmanager.repository;

import com.nuevospa.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}