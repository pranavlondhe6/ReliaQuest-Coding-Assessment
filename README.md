# ReliaQuest's Entry-Level Java Challenge

Please keep the following in mind while working on this challenge:
* Code implementations will not be graded for **correctness** but rather on practicality
* Articulate clear and concise design methodologies, if necessary
* Use clean coding etiquette
  * E.g. avoid liberal use of new-lines, odd variable and method names, random indentation, etc...
* Test cases are not required

## Problem Statement

Your employer has recently purchased a license to top-tier SaaS platform, Employees-R-US, to off-load all employee management responsibilities.
Unfortunately, your company's product has an existing employee management solution that is tightly coupled to other services and therefore 
cannot be replaced whole-cloth. Product and Development leads in your department have decided it would be best to interface
the existing employee management solution with the commercial offering from Employees-R-US for the time being until all employees can be
migrated to the new SaaS platform.

Your ask is to expose employee information as a protected, secure REST API for consumption by Employees-R-US web hooks.
The initial REST API will consist of 3 endpoints, listed in the following section. If for any reason the implementation 
of an endpoint is problematic, the team lead will accept **pseudo-code** and a pertinent description (e.g. java-doc) of intent.

Good luck!

## API Documentation & Reference

All endpoints are secured using **HTTP Basic Authentication**.
- **Credentials:** Username: `admin` | Password: `admin123`
- **Content-Type:** `application/json` (for writes)

---

### 1. Get All Employees
Retrieves all active and terminated employee records in the system.

* **URL:** `/api/v1/employee`
* **Method:** `GET`
* **Expected HTTP Status Codes:**
  * `200 OK` - Successfully retrieved list of employees.
  * `401 Unauthorized` - Missing or invalid credentials.

* **Response Payload Example (200 OK):**
  ```json
  [
    {
      "uuid": "bbb0e3c2-d92e-4d4a-83db-e72f83b0651a",
      "firstName": "Sarah",
      "lastName": "Johnson",
      "fullName": "Sarah Johnson",
      "salary": 85000,
      "age": 32,
      "jobTitle": "Senior Software Engineer",
      "email": "sarah.johnson@company.com",
      "contractHireDate": "2024-07-14T19:12:02.449160Z",
      "contractTerminationDate": null
    }
  ]
  ```

---

### 2. Get Employee by UUID
Retrieves a single employee record based on their unique identifier.

* **URL:** `/api/v1/employee/{uuid}`
* **Method:** `GET`
* **Path Variables:** `uuid` (UUID)
* **Expected HTTP Status Codes:**
  * `200 OK` - Employee record found.
  * `401 Unauthorized` - Missing or invalid credentials.
  * `404 Not Found` - No employee matches the provided UUID.

* **Response Payload Example (200 OK):**
  ```json
  {
    "uuid": "bbb0e3c2-d92e-4d4a-83db-e72f83b0651a",
    "firstName": "Sarah",
    "lastName": "Johnson",
    "fullName": "Sarah Johnson",
    "salary": 85000,
    "age": 32,
    "jobTitle": "Senior Software Engineer",
    "email": "sarah.johnson@company.com",
    "contractHireDate": "2024-07-14T19:12:02.449160Z",
    "contractTerminationDate": null
  }
  ```

* **Response Payload Example (404 Not Found):**
  ```json
  {
    "timestamp": "2026-07-15T22:52:35Z",
    "status": 404,
    "error": "Not Found",
    "message": "Employee not found with UUID: 00000000-0000-0000-0000-000000000000",
    "path": "/api/v1/employee/00000000-0000-0000-0000-000000000000"
  }
  ```

---

### 3. Create Employee
Registers a new employee. Server automatically assigns a new random UUID and computes the full name.

* **URL:** `/api/v1/employee`
* **Method:** `POST`
* **Expected HTTP Status Codes:**
  * `201 Created` - Employee successfully created.
  * `400 Bad Request` - Validation constraints failed (e.g. invalid age, missing fields).
  * `401 Unauthorized` - Missing or invalid credentials.
  * `409 Conflict` - An employee with the provided email address already exists.

* **Request Payload Example:**
  ```json
  {
    "firstName": "Robert",
    "lastName": "Martin",
    "salary": 120000,
    "age": 55,
    "jobTitle": "Software Craftsman",
    "email": "uncle.bob@cleancoder.com",
    "contractHireDate": "2026-07-15T00:00:00Z"
  }
  ```

* **Response Payload Example (201 Created):**
  ```json
  {
    "uuid": "b9a337f9-a2a7-489f-973b-52238d16ea04",
    "firstName": "Robert",
    "lastName": "Martin",
    "fullName": "Robert Martin",
    "salary": 120000,
    "age": 55,
    "jobTitle": "Software Craftsman",
    "email": "uncle.bob@cleancoder.com",
    "contractHireDate": "2026-07-15T00:00:00Z",
    "contractTerminationDate": null
  }
  ```

* **Validation Constraints:**
  - `firstName`, `lastName`, `jobTitle`, `email` must not be blank.
  - `email` must be a valid email address.
  - `salary` must be a non-negative integer (`>= 0`).
  - `age` must be between `18` and `75` inclusive.
  - `contractHireDate` must not be null.

## Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build.

To format code according to style guidelines, you can run **spotlessApply** task.
`./gradlew spotlessApply`

The spotless plugin will also execute check-and-validation tasks as part of the gradle **build** task.
`./gradlew build`
