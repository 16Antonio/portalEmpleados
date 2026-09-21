# 🏢 Gestor de Turnos & CRM/ERP (Proyecto DAM)

![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

Un sistema integral B2B (Business to Business) diseñado para gestionar los recursos humanos, turnos de trabajo y relaciones comerciales de una empresa de servicios. 

Este proyecto fue desarrollado como **Proyecto Intermodular para el Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)**, con un fuerte enfoque en arquitectura limpia, seguridad y buenas prácticas de la industria.

## 🚀 Funcionalidades Principales

### 🔒 Seguridad y Control de Accesos (RBAC)
* Autenticación sin estado basada en **JSON Web Tokens (JWT)**.
* Contraseñas encriptadas mediante **BCrypt**.
* Sistema de **Roles y Permisos Dinámicos**: Los administradores pueden crear nuevos roles asignando permisos granulares (ej: `CREAR_EMPLEADO`, `VER_CUADRANTES`) directamente desde la interfaz gráfica.

### 👥 Recursos Humanos
* Alta, baja y modificación de empleados.
* Asignación dinámica de roles.
* Protección de datos críticos (las contraseñas no se exponen al editar perfiles).

### 📅 Gestión de Cuadrantes (Turnos)
* Motor de reglas de negocio que previene el **solapamiento matemático** de horas al asignar turnos a un empleado.
* Control de disponibilidad del empleado en tiempo real.
* Prevención de asignación de turnos múltiples inválidos en una misma fecha.

## 🛠️ Arquitectura y Buenas Prácticas (Para Reclutadores)

Este proyecto no se limita a ser un simple CRUD; aplica estándares del mundo laboral real:

* **Arquitectura en 3 Capas (MVC):** Separación estricta entre Controladores, Servicios y Repositorios.
* **Patrón DTO (Data Transfer Object):** Prevención de vulnerabilidades de *Mass Assignment* y ocultación de datos sensibles en las respuestas HTTP.
* **Manejo Global de Excepciones:** Uso de `@RestControllerAdvice` para transformar excepciones de Java (`RecursoNoEncontradoException`, `ConflictoException`) en códigos de estado HTTP semánticos (`404 Not Found`, `409 Conflict`).
* **Validación de Datos (Fail-Fast):** Integración de `spring-boot-starter-validation` (`@Valid`, `@NotBlank`) para rechazar peticiones malformadas antes de que alcancen la lógica de negocio o la base de datos.

## 📸 Capturas de Pantalla

*(Añade aquí 2 o 3 capturas de tu Frontend. Por ejemplo, la pantalla de Login, la tabla de Empleados y la vista de los Cuadrantes).*

* `![Pantalla de Login](./docs/LoginScreen.png)`
* `![Gestión de Empleados](./docs/EmpleadoScreen.png)`

## ⚙️ Instalación y Despliegue Local

### Requisitos Previos
* Java 21+
* Node.js y npm
* Base de datos MySQL (Local o en la nube, ej. Aiven)

### Backend (Spring Boot)
1. Navega a la carpeta `/Backend`.
2. Configura tus variables de entorno en tu IDE o en `application.properties` (`DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`).
3. Ejecuta el proyecto mediante Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (React + Vite)
1. Navega a la carpeta `/frontend`.
2. Instala las dependencias:
   ```bash
   npm install
   ```
3. Levanta el servidor de desarrollo:
   ```bash
   npm run dev
   ```

---
**Autor:** [ Antonio Manuel Gámez Pérez / https://www.linkedin.com/in/antoniomgp/]