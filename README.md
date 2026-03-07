# 🚀 Employee Management System

Secure Spring Boot REST API with JWT Authentication and Role-Based Authorization.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT (jjwt 0.11.5)
- Spring Data JPA
- MySQL
- Swagger / OpenAPI
- JUnit 5 + MockMvc

---

## 🔐 Security Architecture

- Stateless JWT Authentication
- BCrypt password hashing
- Role-based authorization using `@PreAuthorize`
- Enum-based role modeling (`Role.USER`, `Role.ADMIN`)
- Global exception handling
- Validation using Jakarta Bean Validation

---

## 👥 Roles & Permissions

| Role  | Permissions |
|-------|------------|
| USER  | View employees |
| ADMIN | Create, update, delete, and view employees |

Roles are implemented using a Java Enum:

```java
public enum Role {
    USER,
    ADMIN
}
```

Stored in database using:

```java
@Enumerated(EnumType.STRING)
private Role role;
```

This prevents invalid role values and improves type safety.

---

## 🔑 Authentication Endpoints

### Register USER
POST `/auth/register`

### Register ADMIN
POST `/auth/register-admin`

### Login
POST `/auth/login`

Returns JWT token.

---

## 📦 Employee Endpoints

### Get All Employees
GET `/api/employees`

### Get Employee By ID
GET `/api/employees/{id}`

### Create Employee (ADMIN only)
POST `/api/employees`

### Update Employee (ADMIN only)
PUT `/api/employees/{id}`

### Delete Employee (ADMIN only)
DELETE `/api/employees/{id}`

### Search
GET `/api/employees/search?name=John`

### Pagination
GET `/api/employees/page?page=0&size=5&sortBy=name&direction=asc`

---

## 🔐 Authorization

JWT must be included in header:

```
Authorization: Bearer <token>
```

---

## 📄 HTTP Status Codes

- 200 → Success
- 400 → Validation error
- 401 → Unauthorized
- 403 → Forbidden
- 404 → Not found
- 409 → Conflict
- 500 → Internal server error

---

## 📘 Swagger

Access:

http://localhost:8080/swagger-ui.html

Use the **Authorize** button to add your JWT token.

---

## 🧪 Testing

Implemented using:

- Spring Boot Test
- MockMvc
- Spring Security Test

Covers:

- Role-based access control
- Validation failures
- Unauthorized access
- Admin permissions

Run tests:

```
mvn test
```

---

## 🏗 Architecture Highlights

- DTO-based architecture
- Mapper layer
- Global exception handling
- Enum-based role modeling
- Secure stateless configuration
- Clean layered structure

---

## ▶ Run Application

```
mvnw clean install
mvnw spring-boot:run
```

---

## 📌 Project Status

✔ JWT Authentication  
✔ Role-Based Authorization  
✔ Enum-Based Role Modeling  
✔ Employee CRUD  
✔ Search & Pagination  
✔ Validation  
✔ Exception Handling  
✔ Swagger Integration  
✔ Integration Tests  
✔ Clean Architecture

---

Built as a production-style backend system demonstrating secure REST API development with Spring Boot.