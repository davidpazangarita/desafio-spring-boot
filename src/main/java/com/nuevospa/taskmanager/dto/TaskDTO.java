package com.nuevospa.taskmanager.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record TaskDTO(
        Long id,
        String title,
        String description,
        String status,
        String username,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
