package com.nuevospa.taskmanager.controller;

import com.nuevospa.taskmanager.dto.*;
import com.nuevospa.taskmanager.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthService authService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("enrique")
                .password("1988")
                .build();

        UserDTO expectedUser = UserDTO.builder()
                .id(1L)
                .username("enrique")
                .build();

        when(authService.register(request)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = authController.register(request);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("admin");
        request.setPassword("admin");

        AuthResponseDTO expectedResponse = new AuthResponseDTO("jwt-token");

        when(authService.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthResponseDTO> response = authController.login(request);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals("jwt-token", response.getBody().getToken());
    }

}