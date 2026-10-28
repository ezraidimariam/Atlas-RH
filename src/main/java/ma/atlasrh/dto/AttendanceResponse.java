package ma.atlasrh.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceResponse(Long id, Long employeeId, String employeeNumber, String employeeName,
                                 LocalDate workDate, LocalDateTime clockIn, LocalDateTime clockOut) {}
