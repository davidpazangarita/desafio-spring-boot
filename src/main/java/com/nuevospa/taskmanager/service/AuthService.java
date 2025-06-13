package com.nuevospa.taskmanager.service;

import com.nuevospa.taskmanager.dto.AuthRequestDTO;
import com.nuevospa.taskmanager.dto.AuthResponseDTO;
import com.nuevospa.taskmanager.dto.RegisterRequestDTO;
import com.nuevospa.taskmanager.dto.UserDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO request);
    UserDTO register(RegisterRequestDTO request);
}