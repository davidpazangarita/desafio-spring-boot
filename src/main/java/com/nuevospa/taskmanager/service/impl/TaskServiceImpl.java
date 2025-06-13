package com.nuevospa.taskmanager.service.impl;

import com.nuevospa.taskmanager.dto.TaskCreateDTO;
import com.nuevospa.taskmanager.dto.TaskDTO;
import com.nuevospa.taskmanager.dto.TaskPartialUpdateDTO;
import com.nuevospa.taskmanager.dto.TaskUpdateDTO;
import com.nuevospa.taskmanager.entity.Task;
import com.nuevospa.taskmanager.entity.TaskStatus;
import com.nuevospa.taskmanager.entity.User;
import com.nuevospa.taskmanager.exception.BusinessException;
import com.nuevospa.taskmanager.exception.UserNotFoundException;
import com.nuevospa.taskmanager.exception.TaskNotFoundException;
import com.nuevospa.taskmanager.mapper.TaskMapper;
import com.nuevospa.taskmanager.repository.TaskRepository;
import com.nuevospa.taskmanager.repository.TaskStatusRepository;
import com.nuevospa.taskmanager.repository.UserRepository;
import com.nuevospa.taskmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository statusRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           TaskStatusRepository statusRepository,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO create(TaskCreateDTO dto) {
        log.info("Creando tarea para usuario: {}", dto.getUsername());

        User user = getUserByUsername(dto.getUsername());
        var status = getStatusByName(dto.getStatus());

        Task task = taskMapper.toEntity(dto, status, user);
        var saved = taskRepository.save(task);

        return taskMapper.toDTO(saved);
    }

    @Override
    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public TaskDTO update(Long id, TaskUpdateDTO dto) {
        log.info("Actualizando tarea con ID: {}", id);

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskMapper.updateEntity(task, dto);

        Optional.ofNullable(dto.getStatus())
                .ifPresent(status -> task.setStatus(getStatusByName(status)));

        Optional.ofNullable(dto.getUsername())
                .ifPresent(username -> task.setUser(getUserByUsername(username)));

        var updated = taskRepository.save(task);
        return taskMapper.toDTO(updated);
    }

    @Override
    public TaskDTO partialUpdate(Long id, TaskPartialUpdateDTO dto) {
        log.info("Actualización parcial de tarea con ID: {}", id);

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        Optional.ofNullable(dto.getTitle()).ifPresent(task::setTitle);
        Optional.ofNullable(dto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(dto.getStatus())
                .ifPresent(status -> task.setStatus(getStatusByName(status)));
        Optional.ofNullable(dto.getUsername())
                .ifPresent(username -> task.setUser(getUserByUsername(username)));

        var updated = taskRepository.save(task);
        return taskMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Eliminando tarea con ID: {}", id);

        if (!taskRepository.existsById(id)) {
            log.warn("Intento de eliminar tarea inexistente con ID: {}", id);
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private TaskStatus getStatusByName(String statusName) {
        return statusRepository.findByNameIgnoreCase(statusName)
                .orElseThrow(() -> new BusinessException(String.format("Estado '%s' no válido", statusName)));
    }
}