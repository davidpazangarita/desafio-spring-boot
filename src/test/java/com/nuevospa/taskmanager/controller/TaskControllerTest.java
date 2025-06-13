package com.nuevospa.taskmanager.controller;

import com.nuevospa.taskmanager.entity.Task;
import com.nuevospa.taskmanager.entity.TaskStatus;
import com.nuevospa.taskmanager.entity.User;
import com.nuevospa.taskmanager.repository.TaskRepository;
import com.nuevospa.taskmanager.repository.TaskStatusRepository;
import com.nuevospa.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    private User user;
    private TaskStatus status;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        taskStatusRepository.deleteAll();

        user = new User();
        user.setUsername("david");
        user.setPassword("123");
        user = userRepository.save(user);

        status = new TaskStatus();
        status.setName("PENDING");
        status = taskStatusRepository.save(status);
    }

    @Test
    @WithMockUser(username = "david", roles = {"USER"})
    void testCreateTask() throws Exception {
        String request = String.format("""
            {
              "title": "Prueba Integrada",
              "description": "Test sin MockBean",
              "status": "%s",
              "username": "%s"
            }
        """, status.getName(), user.getUsername());

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Prueba Integrada"));
    }

    @Test
    @WithMockUser(username = "david", roles = {"USER"})
    void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setTitle("Task Real");
        task.setDescription("desc");
        task.setStatus(status);
        task.setUser(user);
        task = taskRepository.save(task);

        mockMvc.perform(get("/api/v1/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task Real"))
                .andExpect(jsonPath("$.username").value("david"));
    }
}