package com.nuevospa.taskmanager.dto;

import lombok.Builder;

@Builder
public record UserDTO(Long id, String username, String role) {
}
