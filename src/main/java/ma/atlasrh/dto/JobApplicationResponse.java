package ma.atlasrh.dto;

import ma.atlasrh.entity.ApplicationStatus;
import java.time.LocalDateTime;

public record JobApplicationResponse(Long id, Long jobOpeningId, String fullName, String email,
                                     String phone, String resumeUrl, LocalDateTime appliedAt,
                                     ApplicationStatus status) {}
