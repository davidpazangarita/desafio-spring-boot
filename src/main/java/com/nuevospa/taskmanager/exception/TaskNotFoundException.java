package com.nuevospa.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Tarea con ID " + id + " no encontrada");
    }
}