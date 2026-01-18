# Employee Management System (Spring Boot)

A secure Employee Management System built using **Spring Boot**, **JWT authentication**, and **role-based authorization**.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Maven

---

## Features

- User & Admin authentication using JWT
- Role-based access control
- Employee CRUD operations
- Admin-only management endpoints
- Validation & global exception handling

---

## Roles & Permissions

### USER
- Login
- View employees (READ ONLY)

### ADMIN
- Login
- Create employees
- Update employees
- Delete employees
- View all employees (admin endpoints)

---

## API Endpoints

### Authentication

#### Register User
