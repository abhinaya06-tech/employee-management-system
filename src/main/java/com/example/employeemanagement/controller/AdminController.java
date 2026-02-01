package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/employees")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ===============================
    // ADMIN: View all employees
    // ===============================
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // ===============================
    // ADMIN: Paginated & Sorted list
    // Example:
    // /admin/employees/page?page=0&size=5&sortBy=name&direction=asc
    // ===============================
    @GetMapping("/page")
    public Page<EmployeeDTO> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return employeeService.getEmployeesPaginated(
                page, size, sortBy, direction
        );
    }

    // ===============================
    // ADMIN: Delete any employee
    // ===============================
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted by admin";
    }
}
