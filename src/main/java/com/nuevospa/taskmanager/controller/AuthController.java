package com.nuevospa.taskmanager.controller;

import com.nuevospa.taskmanager.dto.AuthRequestDTO;
import com.nuevospa.taskmanager.dto.AuthResponseDTO;
import com.nuevospa.taskmanager.dto.RegisterRequestDTO;
import com.nuevospa.taskmanager.dto.UserDTO;
import com.nuevospa.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        var response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequestDTO request) {
        UserDTO user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}