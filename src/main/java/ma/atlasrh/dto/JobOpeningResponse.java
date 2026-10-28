package ma.atlasrh.dto;

import ma.atlasrh.entity.JobOpeningStatus;
import java.time.LocalDate;

public record JobOpeningResponse(Long id, String title, String description, Long departmentId,
                                 String departmentName, LocalDate openedOn, LocalDate closesOn,
                                 JobOpeningStatus status) {}
