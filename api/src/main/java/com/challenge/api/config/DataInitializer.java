package com.challenge.api.config;

import com.challenge.api.model.EmployeeEntity;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final ConcurrentMap<UUID, EmployeeEntity> employeeStore;
    private final ConcurrentMap<String, EmployeeEntity> emailStore;

    public DataInitializer(
            ConcurrentMap<UUID, EmployeeEntity> employeeStore, ConcurrentMap<String, EmployeeEntity> emailStore) {
        this.employeeStore = employeeStore;
        this.emailStore = emailStore;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing employee data store with dummy records");

        Instant now = Instant.now();

        List<EmployeeEntity> employees = List.of(
                EmployeeEntity.builder()
                        .uuid(UUID.randomUUID())
                        .firstName("Pranav")
                        .lastName("Londhe")
                        .fullName("Pranav Londhe")
                        .salary(85000)
                        .age(22)
                        .jobTitle("Senior Software Engineer")
                        .email("pranav.londhe@company.com")
                        .contractHireDate(now.minus(730, ChronoUnit.DAYS))
                        .build(),
                EmployeeEntity.builder()
                        .uuid(UUID.randomUUID())
                        .firstName("Priya")
                        .lastName("Patil")
                        .fullName("Priya Patil")
                        .salary(72000)
                        .age(28)
                        .jobTitle("DevOps Engineer")
                        .email("priya.patil@company.com")
                        .contractHireDate(now.minus(365, ChronoUnit.DAYS))
                        .build(),
                EmployeeEntity.builder()
                        .uuid(UUID.randomUUID())
                        .firstName("Om")
                        .lastName("Bankar")
                        .fullName("Om Bankar")
                        .salary(95000)
                        .age(35)
                        .jobTitle("Engineering Manager")
                        .email("om.bankar@company.com")
                        .contractHireDate(now.minus(1095, ChronoUnit.DAYS))
                        .build(),
                EmployeeEntity.builder()
                        .uuid(UUID.randomUUID())
                        .firstName("Shri")
                        .lastName("Chobhe")
                        .fullName("Shri Chobhe")
                        .salary(68000)
                        .age(25)
                        .jobTitle("Junior Backend Developer")
                        .email("shri.Chobhe@company.com")
                        .contractHireDate(now.minus(180, ChronoUnit.DAYS))
                        .build(),
                EmployeeEntity.builder()
                        .uuid(UUID.randomUUID())
                        .firstName("Rahul")
                        .lastName("Bhukan")
                        .fullName("Rahul Bhukan")
                        .salary(110000)
                        .age(40)
                        .jobTitle("Principal Architect")
                        .email("rahul.bhukan@company.com")
                        .contractHireDate(now.minus(1825, ChronoUnit.DAYS))
                        .contractTerminationDate(now.minus(30, ChronoUnit.DAYS))
                        .build());

        employees.forEach(employee -> {
            employeeStore.putIfAbsent(employee.getUuid(), employee);
            emailStore.putIfAbsent(employee.getEmail().toLowerCase(), employee);
            log.debug(
                    "Loaded employee: {} - {} ({})",
                    employee.getUuid(),
                    employee.getFullName(),
                    employee.getJobTitle());
        });

        log.info("Employee data store initialized with {} records", employeeStore.size());
    }
}
