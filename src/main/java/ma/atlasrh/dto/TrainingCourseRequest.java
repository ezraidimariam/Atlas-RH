package ma.atlasrh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record TrainingCourseRequest(@NotBlank @Size(max = 150) String title,
                                    @Size(max = 4000) String description,
                                    @Size(max = 150) String provider,
                                    @NotNull @Positive BigDecimal durationHours) {}
