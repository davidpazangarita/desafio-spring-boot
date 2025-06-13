package com.nuevospa.taskmanager.service.impl;

import com.nuevospa.taskmanager.dto.TaskCreateDTO;
import com.nuevospa.taskmanager.dto.TaskDTO;
import com.nuevospa.taskmanager.dto.TaskUpdateDTO;
import com.nuevospa.taskmanager.entity.Task;
import com.nuevospa.taskmanager.entity.TaskStatus;
import com.nuevospa.taskmanager.entity.User;
import com.nuevospa.taskmanager.exception.BusinessException;
import com.nuevospa.taskmanager.exception.TaskNotFoundException;
import com.nuevospa.taskmanager.exception.UserNotFoundException;
import com.nuevospa.taskmanager.mapper.TaskMapper;
import com.nuevospa.taskmanager.repository.TaskRepository;
import com.nuevospa.taskmanager.repository.TaskStatusRepository;
import com.nuevospa.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TaskStatusRepository statusRepository;
    private TaskMapper taskMapper;
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        statusRepository = mock(TaskStatusRepository.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskServiceImpl(taskRepository, userRepository, statusRepository, taskMapper);
    }

    @Test
    void createTaskSuccessfully() {
        var dto = TaskCreateDTO.builder()
                .title("tarea 1")
                .description("esta es una tarea")
                .username("admin")
                .status("progreso")
                .build();

        var mockUser = new User();
        var mockStatus = new TaskStatus();
        var mockTask = new Task();
        var savedTask = new Task();

        var expectedDTO = TaskDTO.builder()
                .id(1L)
                .title("tarea 1")
                .description("esta es una tarea")
                .status("progreso")
                .username("admin")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userRepository.findByUsernameIgnoreCase("admin")).thenReturn(Optional.of(mockUser));
        when(statusRepository.findByNameIgnoreCase("progreso")).thenReturn(Optional.of(mockStatus));
        when(taskMapper.toEntity(dto, mockStatus, mockUser)).thenReturn(mockTask);
        when(taskRepository.save(mockTask)).thenReturn(savedTask);
        when(taskMapper.toDTO(savedTask)).thenReturn(expectedDTO);

        TaskDTO result = taskService.create(dto);

        assertEquals(expectedDTO, result);
    }

    @Test
    void createTaskThrowsWhenUserNotFound() {
        var dto = TaskCreateDTO.builder()
                .username("nouser")
                .build();

        when(userRepository.findByUsernameIgnoreCase("ghost")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> taskService.create(dto));
    }

    @Test
    void createTaskThrowsWhenStatusInvalid() {
        var dto = TaskCreateDTO.builder()
                .username("admin")
                .status("inexistente")
                .build();

        var mockUser = new User();
        when(userRepository.findByUsernameIgnoreCase("admin")).thenReturn(Optional.of(mockUser));
        when(statusRepository.findByNameIgnoreCase("inexistente")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> taskService.create(dto));
    }

    @Test
    void updateTaskSuccessfully() {
        Long taskId = 1L;
        var dto = TaskUpdateDTO.builder()
                .status("FINALIZADO")
                .username("usuarionuevo")
                .build();

        Task existingTask = new Task();
        TaskStatus newStatus = new TaskStatus();
        User newUser = new User();
        Task updatedTask = new Task();

        var now = LocalDateTime.now();

        TaskDTO expectedDTO = TaskDTO.builder()
                .id(taskId)
                .title("Título actualizado")
                .description("Descripción actualizada")
                .status("FINALIZADO")
                .username("usuarionuevo")
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(statusRepository.findByNameIgnoreCase("FINALIZADO")).thenReturn(Optional.of(newStatus));
        when(userRepository.findByUsernameIgnoreCase("usuarionuevo")).thenReturn(Optional.of(newUser));

        doNothing().when(taskMapper).updateEntity(existingTask, dto);
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);
        when(taskMapper.toDTO(updatedTask)).thenReturn(expectedDTO);

        TaskDTO result = taskService.update(taskId, dto);

        assertEquals(expectedDTO, result);
    }

    @Test
    void updateTaskThrowsWhenTaskNotFound() {
        Long taskId = 999L;
        TaskUpdateDTO dto = TaskUpdateDTO.builder()
                .title("cualquier título")
                .description("desc")
                .status("PENDIENTE")
                .username("user")
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.update(taskId, dto));
    }

    @Test
    void deleteTaskSuccessfully() {
        Long taskId = 10L;
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        assertDoesNotThrow(() -> taskService.delete(taskId));
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTaskThrowsWhenNotFound() {
        Long taskId = 404L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.delete(taskId));
    }

}