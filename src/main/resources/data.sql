-- Insertar usuarios
INSERT INTO users (username, password, role) VALUES ('david', '$2a$10$Db8ENQ5cqONo5OzNatPwv.QQvvLQwZeoWF3M2WDUQx7c8/YySqNXO', 'USER');
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$GyhkdAQHLhoFP49gn1wHa.BlhaqrI6A2Zi8NxNarnnBWn9eMNZ0k6', 'ADMIN');
-- Insertar estados de tarea
INSERT INTO task_status (name) VALUES ('PENDIENTE');
INSERT INTO task_status (name) VALUES ('PROGRESO');
INSERT INTO task_status (name) VALUES ('COMPLETADA');