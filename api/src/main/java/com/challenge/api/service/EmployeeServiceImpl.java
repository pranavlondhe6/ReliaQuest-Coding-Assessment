package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.dto.EmployeeResponse;
import com.challenge.api.exception.DuplicateEmployeeException;
import com.challenge.api.exception.EmployeeNotFoundException;
import com.challenge.api.model.EmployeeEntity;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Thread-safe implementation of {@link EmployeeService} backed by a {@link ConcurrentHashMap}.
 * All mutating operations use atomic map methods to prevent race conditions.
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ConcurrentMap<UUID, EmployeeEntity> employeeStore;
    private final ConcurrentMap<String, EmployeeEntity> emailStore;

    public EmployeeServiceImpl(
            ConcurrentMap<UUID, EmployeeEntity> employeeStore, ConcurrentMap<String, EmployeeEntity> emailStore) {
        this.employeeStore = employeeStore;
        this.emailStore = emailStore;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        log.info("Retrieving all employees, current count: {}", employeeStore.size());
        List<EmployeeResponse> employees = employeeStore.values().stream()
                .map(EmployeeResponse::fromEntity)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(employees);
    }

    @Override
    public EmployeeResponse getEmployeeByUuid(UUID uuid) {
        log.info("Retrieving employee with UUID: {}", uuid);
        EmployeeEntity employee = java.util.Optional.ofNullable(employeeStore.get(uuid))
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));
        log.debug("Employee found: {} {}", employee.getFirstName(), employee.getLastName());
        return EmployeeResponse.fromEntity(employee);
    }

    @Override
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        log.info("Creating employee with email: {}", request.getEmail());

        EmployeeEntity entity = EmployeeEntity.builder()
                .uuid(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .salary(request.getSalary())
                .age(request.getAge())
                .jobTitle(request.getJobTitle())
                .email(request.getEmail())
                .contractHireDate(request.getContractHireDate())
                .build();

        EmployeeEntity existingEmailEmployee =
                emailStore.putIfAbsent(request.getEmail().toLowerCase(), entity);
        if (existingEmailEmployee != null) {
            throw new DuplicateEmployeeException(request.getEmail());
        }

        EmployeeEntity existingUuidEmployee = employeeStore.putIfAbsent(entity.getUuid(), entity);
        if (existingUuidEmployee != null) {
            log.warn("UUID collision detected, regenerating for employee: {}", request.getEmail());
            UUID newUuid = UUID.randomUUID();
            entity.setUuid(newUuid);
            employeeStore.put(newUuid, entity);
        }

        log.info("Employee created successfully with UUID: {}", entity.getUuid());
        return EmployeeResponse.fromEntity(entity);
    }
}
