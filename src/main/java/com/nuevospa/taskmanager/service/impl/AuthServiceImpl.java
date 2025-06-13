package com.nuevospa.taskmanager.service.impl;

import com.nuevospa.taskmanager.dto.AuthRequestDTO;
import com.nuevospa.taskmanager.dto.AuthResponseDTO;
import com.nuevospa.taskmanager.dto.RegisterRequestDTO;
import com.nuevospa.taskmanager.dto.UserDTO;
import com.nuevospa.taskmanager.entity.User;
import com.nuevospa.taskmanager.exception.BusinessException;
import com.nuevospa.taskmanager.mapper.UserMapper;
import com.nuevospa.taskmanager.repository.UserRepository;
import com.nuevospa.taskmanager.security.JwtUtil;
import com.nuevospa.taskmanager.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public static final String USER = "USER";

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                           UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);
        return new AuthResponseDTO(token);
    }

    @Override
    public UserDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new BusinessException("Username ya existe");
        }

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(USER)
                .build();

        var saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }
}
