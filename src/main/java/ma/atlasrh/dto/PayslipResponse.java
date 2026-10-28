package ma.atlasrh.dto;

import java.math.BigDecimal;

public record PayslipResponse(Long id, Long employeeId, String employeeName, int periodYear,
                              int periodMonth, BigDecimal grossSalary, BigDecimal additions,
                              BigDecimal deductions, BigDecimal netSalary) {}
