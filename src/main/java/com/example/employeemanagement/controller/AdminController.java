package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.response.ApiResponse;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employees")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ===============================
    // ADMIN: Paginated list
    // ===============================

    @GetMapping
    public ApiResponse<Page<EmployeeDTO>> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        Page<EmployeeDTO> employees =
                employeeService.getEmployeesPaginated(page, size, sortBy, direction);

        return new ApiResponse<>(
                "SUCCESS",
                "Employees fetched successfully",
                employees
        );
    }

}