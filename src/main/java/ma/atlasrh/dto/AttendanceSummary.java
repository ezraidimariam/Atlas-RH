package ma.atlasrh.dto;

import java.time.LocalDate;

public record AttendanceSummary(Long employeeId, LocalDate from, LocalDate to,
                                long recordedDays, long incompleteDays, long workedMinutes) {}
