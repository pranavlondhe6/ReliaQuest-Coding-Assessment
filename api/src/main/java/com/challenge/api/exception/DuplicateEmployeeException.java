package com.challenge.api.exception;

public class DuplicateEmployeeException extends RuntimeException {

    // To counter duplicacte Employees

    public DuplicateEmployeeException(String email) {
        super("Employee already exists with email: " + email);
    }
}
