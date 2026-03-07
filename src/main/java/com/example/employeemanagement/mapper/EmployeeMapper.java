package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
        // prevent instantiation
    }

    // Used for CREATE operations (no ID set)
    public static Employee toEntity(EmployeeDTO dto) {
        return new Employee(
                dto.getName(),
                dto.getEmail()
        );
    }

    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail()
        );
    }
}