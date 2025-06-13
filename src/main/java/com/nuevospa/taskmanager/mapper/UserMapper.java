package com.nuevospa.taskmanager.mapper;

import com.nuevospa.taskmanager.dto.UserDTO;
import com.nuevospa.taskmanager.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        if (dto.id() != null) user.setId(dto.id());

        if (StringUtils.hasText(dto.username())) {
            user.setUsername(dto.username().trim());
        }

        if (dto.role() != null) {
            user.setRole(dto.role());
        }

        return user;
    }
}