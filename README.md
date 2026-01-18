# Employee Management System

A Spring Boot REST API with JWT-based authentication and role-based authorization.

This project demonstrates a secure backend system where **ADMIN** and **USER**
roles have clearly separated access.

---

## Features

- JWT Authentication (Login & Register)
- Role-based Authorization (USER / ADMIN)
- Secure REST APIs using Spring Security
- CRUD operations for Employees
- Admin-only management APIs
- Global exception handling
- DTO-based architecture
- MySQL / H2 support
- Swagger (OpenAPI) documentation

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Maven
- Swagger (OpenAPI)

---

## Roles & Permissions

### USER
- Login
- View employees (READ only)
- Cannot create, update, or delete employees

### ADMIN
- Login
- Create employees
- Update employees
- Delete employees
- View all employees
- Access admin-only endpoints

---

## API Endpoints

### Authentication
- `POST /auth/register` → Register USER
- `POST /auth/register-admin` → Register ADMIN (ADMIN only)
- `POST /auth/login` → Login & get JWT token

### Employee APIs
- `GET /api/employees` → View employees (USER, ADMIN)
- `GET /api/employees/{id}` → View employee by ID (USER, ADMIN)
- `POST /api/employees` → Create employee (ADMIN only)
- `PUT /api/employees/{id}` → Update employee (ADMIN only)
- `DELETE /api/employees/{id}` → Delete employee (ADMIN only)

### Admin APIs
- `GET /admin/employees` → View all employees (ADMIN only)

---

## Security

- JWT is required for all protected endpoints
- Unauthorized access returns HTTP 403
- Passwords are stored using BCrypt hashing
- Stateless session management
