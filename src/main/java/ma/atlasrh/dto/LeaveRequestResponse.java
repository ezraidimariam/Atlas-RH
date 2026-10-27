package ma.atlasrh.dto;

import ma.atlasrh.entity.LeaveStatus;
import java.time.LocalDate;

public record LeaveRequestResponse(Long id, Long employeeId, String employeeNumber,
                                   String employeeName, LocalDate startDate, LocalDate endDate,
                                   String reason, LeaveStatus status) {}
