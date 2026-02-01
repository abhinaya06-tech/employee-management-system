package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.mapper.EmployeeMapper;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ================= CREATE =================
    // ADMIN ONLY (enforced at controller level)
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        return EmployeeMapper.toDTO(employeeRepository.save(employee));
    }

    // ================= READ =================
    // USER + ADMIN
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id " + id
                        ));
        return EmployeeMapper.toDTO(employee);
    }

    // USER + ADMIN (non-paginated)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // USER + ADMIN (search)
    public List<EmployeeDTO> searchEmployees(String name) {
        return employeeRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ================= UPDATE =================
    // ADMIN ONLY
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

    // ================= DELETE =================
    // ADMIN ONLY
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(
                    "Employee not found with id " + id
            );
        }
        employeeRepository.deleteById(id);
    }

    // ================= PAGINATION =================
    // ADMIN ONLY (used by AdminController)
    public Page<EmployeeDTO> getEmployeesPaginated(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::toDTO);
    }
}