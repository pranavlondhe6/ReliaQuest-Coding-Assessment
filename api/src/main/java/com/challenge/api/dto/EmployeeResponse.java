package com.challenge.api.dto;

import com.challenge.api.model.Employee;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeResponse {

    UUID uuid;
    String firstName;
    String lastName;
    String fullName;
    Integer salary;
    Integer age;
    String jobTitle;
    String email;
    Instant contractHireDate;
    Instant contractTerminationDate;

    public static EmployeeResponse fromEntity(Employee employee) {
        return EmployeeResponse.builder()
                .uuid(employee.getUuid())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fullName(employee.getFullName())
                .salary(employee.getSalary())
                .age(employee.getAge())
                .jobTitle(employee.getJobTitle())
                .email(employee.getEmail())
                .contractHireDate(employee.getContractHireDate())
                .contractTerminationDate(employee.getContractTerminationDate())
                .build();
    }
}
