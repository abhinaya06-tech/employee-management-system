package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.mapper.EmployeeMapper;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "email");

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        return EmployeeMapper.toDTO(employeeRepository.save(employee));
    }

    // READ BY ID
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toDTO)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id " + id
                        ));
    }

    // READ ALL
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // SEARCH
    public List<EmployeeDTO> searchEmployees(String name) {
        return employeeRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id " + id
                        ));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());

        return EmployeeMapper.toDTO(employeeRepository.save(existing));
    }

    // DELETE (single DB call)
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id " + id
                        ));

        employeeRepository.delete(existing);
    }

    // PAGINATION
    public Page<EmployeeDTO> getEmployeesPaginated(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            sortBy = "id";
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::toDTO);
    }
}