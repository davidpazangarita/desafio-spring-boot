# 🛡️ Security Test Strategy - TaskManager API

Este documento describe la estrategia de pruebas de seguridad adoptada en el proyecto **TaskManager**, desarrollado con Spring Boot y Spring Security.

---

## 🔐 Enfoque de Seguridad en Producción

- La autenticación en producción se realizaria mediante **JWT (JSON Web Tokens)**.
- Los endpoints están protegidos con Spring Security, y requieren un token válido en el header `Authorization: Bearer <token>`.
- Las rutas públicas como `/auth/login` y `/auth/register` están explícitamente permitidas sin autenticación.

---

## 🧪 Estrategia de pruebas de seguridad

### ✅ Nivel: Pruebas de Controlador

- Se usan pruebas con `@SpringBootTest` y `MockMvc`.
- Se simula un usuario autenticado usando `@WithMockUser`.
- Esto evita la ejecución del filtro real de autenticación JWT en entorno de pruebas.

#### 🔍 ¿Por qué `@WithMockUser`?
- Es simple, expresivo y enfocado en el comportamiento de los endpoints.
- Permite probar reglas de autorización sin requerir tokens reales.
- Es compatible con `MockMvc` y parte de la librería oficial `spring-security-test`.

### 🛠️ Ejemplo de uso

```java
@WithMockUser(username = "david", roles = {"USER"})
@Test
void testCreateTask() throws Exception {
    mockMvc.perform(post("/api/v1/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{...}"))
        .andExpect(status().isCreated());
}
