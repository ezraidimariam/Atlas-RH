package ma.atlasrh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record JobOpeningRequest(@NotBlank @Size(max = 120) String title,
                                @NotBlank @Size(max = 4000) String description,
                                @NotNull Long departmentId, @NotNull LocalDate openedOn,
                                LocalDate closesOn) {}
