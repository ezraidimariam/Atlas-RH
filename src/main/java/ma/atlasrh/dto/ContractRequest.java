package ma.atlasrh.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import ma.atlasrh.entity.ContractType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractRequest(@NotNull ContractType type, @NotNull LocalDate startDate,
                              LocalDate endDate, @NotNull @PositiveOrZero BigDecimal grossSalary,
                              @NotNull Long employeeId) {}
