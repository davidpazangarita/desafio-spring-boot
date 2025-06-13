package com.nuevospa.taskmanager.service;

import com.nuevospa.taskmanager.dto.UserDTO;
import com.nuevospa.taskmanager.dto.RegisterRequestDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findByUsername(String username);
}
