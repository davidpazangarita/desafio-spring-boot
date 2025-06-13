package com.nuevospa.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequestDTO {
    @NotBlank(message = "username no puede estar vacío")
    private String username;

    @NotBlank(message = "password no puede estar vacío")
    private String password;
}