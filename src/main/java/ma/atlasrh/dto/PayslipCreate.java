package ma.atlasrh.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record PayslipCreate(@NotNull Long employeeId,
                            @Min(2000) @Max(2100) int periodYear,
                            @Min(1) @Max(12) int periodMonth,
                            @NotNull @PositiveOrZero BigDecimal grossSalary,
                            @NotNull @PositiveOrZero BigDecimal additions,
                            @NotNull @PositiveOrZero BigDecimal deductions) {}
