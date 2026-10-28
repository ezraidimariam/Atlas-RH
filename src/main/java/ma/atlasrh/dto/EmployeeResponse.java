package ma.atlasrh.dto;

import java.time.LocalDate;

public record EmployeeResponse(Long id, String employeeNumber, String lastName, String firstName,
                               String email, String jobTitle, LocalDate hireDate,
                               Long departmentId, String departmentName, Long managerId, boolean active) {}
