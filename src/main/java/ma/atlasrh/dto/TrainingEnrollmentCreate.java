package ma.atlasrh.dto;

import jakarta.validation.constraints.NotNull;

public record TrainingEnrollmentCreate(@NotNull Long employeeId, @NotNull Long courseId) {}
