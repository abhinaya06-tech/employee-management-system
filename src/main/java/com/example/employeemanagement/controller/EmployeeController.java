package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ================= READ (ADMIN ONLY) =================

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<EmployeeDTO>> searchEmployees(
            @RequestParam String name) {

        return new ApiResponse<>(
                "SUCCESS",
                "Employees fetched successfully",
                employeeService.searchEmployees(name)
        );
    }

    // ================= WRITE (ADMIN ONLY) =================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return employeeService.saveEmployee(dto);
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
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }
}
