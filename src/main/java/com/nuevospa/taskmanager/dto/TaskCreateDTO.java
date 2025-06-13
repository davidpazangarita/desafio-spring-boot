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
public class TaskCreateDTO {

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "El estado no puede estar vacío")
    private String status;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
}
