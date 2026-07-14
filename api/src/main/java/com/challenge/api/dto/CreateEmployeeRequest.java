package com.challenge.api.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Salary is required") @Min(value = 0, message = "Salary must be a non-negative value")
    private Integer salary;

    @NotNull(message = "Age is required") @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 75, message = "Age must not exceed 75")
    private Integer age;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotNull(message = "Contract hire date is required") private Instant contractHireDate;
}
