package ma.atlasrh.dto;

import ma.atlasrh.entity.TrainingStatus;
import java.time.LocalDate;

public record TrainingEnrollmentResponse(Long id, Long employeeId, String employeeName,
                                         Long courseId, String courseTitle, LocalDate enrolledOn,
                                         LocalDate completedOn, TrainingStatus status) {}
