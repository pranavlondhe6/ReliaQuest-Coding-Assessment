package com.challenge.api.controller;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.dto.EmployeeResponse;
import com.challenge.api.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing employee management endpoints.
 * Delegates all business logic to {@link EmployeeService} — no domain logic resides here.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @return All employees, unfiltered.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        log.info("GET /api/v1/employee - Retrieving all employees");
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> getEmployeeByUuid(@PathVariable UUID uuid) {
        log.info("GET /api/v1/employee/{} - Retrieving employee by UUID", uuid);
        EmployeeResponse employee = employeeService.getEmployeeByUuid(uuid);
        return ResponseEntity.ok(employee);
    }

    /**
     * @param request Validated employee creation request
     * @return Newly created Employee
     */
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        log.info("POST /api/v1/employee - Creating new employee: {} {}", request.getFirstName(), request.getLastName());
        EmployeeResponse employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
}
