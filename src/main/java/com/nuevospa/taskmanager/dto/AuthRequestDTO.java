package com.nuevospa.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @NotBlank(message = "username no puede estar vacío")
    private String username;

    @NotBlank(message = "password no puede estar vacío")
    private String password;
}

