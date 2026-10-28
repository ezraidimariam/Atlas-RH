package ma.atlasrh.dto;

import java.math.BigDecimal;

public record TrainingCourseResponse(Long id, String title, String description,
                                     String provider, BigDecimal durationHours) {}
