package com.nuevospa.taskmanager.service.impl;

import com.nuevospa.taskmanager.dto.UserDTO;
import com.nuevospa.taskmanager.mapper.UserMapper;
import com.nuevospa.taskmanager.repository.UserRepository;
import com.nuevospa.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(userMapper::toDTO);
    }

}



