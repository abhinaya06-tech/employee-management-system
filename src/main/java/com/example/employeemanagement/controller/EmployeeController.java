package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    // ================= READ (USER + ADMIN) =================

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResponse<List<EmployeeDTO>> searchEmployees(
            @RequestParam String name) {

        return new ApiResponse<>(
                "SUCCESS",
                "Employees fetched successfully",
                employeeService.searchEmployees(name)
        );
    }

    // ================= PAGINATION (USER + ADMIN) =================

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<EmployeeDTO> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return employeeService.getEmployeesPaginated(page, size, sortBy, direction);
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
