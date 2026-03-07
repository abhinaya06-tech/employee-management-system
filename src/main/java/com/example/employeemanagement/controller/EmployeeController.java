package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ================= READ =================

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        return new ApiResponse<>(
                "SUCCESS",
                "Employees fetched successfully",
                employeeService.getAllEmployees()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResponse<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return new ApiResponse<>(
                "SUCCESS",
                "Employee fetched successfully",
                employeeService.getEmployeeById(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeDTO> createEmployee(
            @Valid @RequestBody EmployeeDTO dto) {

        return new ApiResponse<>(
                "SUCCESS",
                "Employee created successfully",
                employeeService.saveEmployee(dto)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO dto) {

        return new ApiResponse<>(
                "SUCCESS",
                "Employee updated successfully",
                employeeService.updateEmployee(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return new ApiResponse<>(
                "SUCCESS",
                "Employee deleted successfully",
                null
        );
    }
}