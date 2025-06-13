package com.nuevospa.taskmanager.mapper;

import com.nuevospa.taskmanager.dto.TaskCreateDTO;
import com.nuevospa.taskmanager.dto.TaskDTO;
import com.nuevospa.taskmanager.dto.TaskUpdateDTO;
import com.nuevospa.taskmanager.entity.Task;
import com.nuevospa.taskmanager.entity.TaskStatus;
import com.nuevospa.taskmanager.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) return null;

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus() != null ? task.getStatus().getName() : null)
                .username(task.getUser() != null ? task.getUser().getUsername() : null)
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    public Task toEntity(TaskCreateDTO dto, TaskStatus status, User user) {
        if (dto == null) return null;

        Task task = new Task();
        task.setTitle(StringUtils.hasText(dto.getTitle()) ? dto.getTitle().trim() : null);
        task.setDescription(StringUtils.hasText(dto.getDescription()) ? dto.getDescription().trim() : null);
        task.setStatus(status);
        task.setUser(user);
        return task;
    }

    public void updateEntity(Task task, TaskUpdateDTO dto) {
        if (task == null || dto == null) return;

        if (StringUtils.hasText(dto.getTitle())) {
            task.setTitle(dto.getTitle().trim());
        }

        if (StringUtils.hasText(dto.getDescription())) {
            task.setDescription(dto.getDescription().trim());
        }
    }
}