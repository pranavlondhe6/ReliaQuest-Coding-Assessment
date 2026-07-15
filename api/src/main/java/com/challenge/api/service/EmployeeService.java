package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.dto.EmployeeResponse;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees();

    /**
     * Retrieves a single employee by their unique identifier.
     *
     * @param uuid the employee UUID
     * @return the matching employee response
     * @throws com.challenge.api.exception.EmployeeNotFoundException if no employee is found
     */
    EmployeeResponse getEmployeeByUuid(UUID uuid);

    /**
     * Creates a new employee record.
     *
     * @param request the validated creation request
     * @return the newly created employee response
     * @throws com.challenge.api.exception.DuplicateEmployeeException if the email already exists
     */
    EmployeeResponse createEmployee(CreateEmployeeRequest request);
}
