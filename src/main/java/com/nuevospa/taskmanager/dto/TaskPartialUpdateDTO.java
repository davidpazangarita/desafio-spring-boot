package com.nuevospa.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskPartialUpdateDTO {

    private String title;
    private String description;
    private String status;
    private String username;
}