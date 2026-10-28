package ma.atlasrh.service;

import ma.atlasrh.dto.PayslipCreate;
import ma.atlasrh.dto.PayslipResponse;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.entity.Payslip;
import ma.atlasrh.repository.EmployeeRepository;
import ma.atlasrh.repository.PayslipRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PayslipService {
    private final PayslipRepository payslips;
    private final EmployeeRepository employees;
    public PayslipService(PayslipRepository payslips, EmployeeRepository employees) { this.payslips = payslips; this.employees = employees; }

    @Transactional(readOnly = true)
    public List<PayslipResponse> find(Long employeeId) {
        List<Payslip> result = employeeId == null ? payslips.findAll() : payslips.findByEmployeeIdOrderByPeriodYearDescPeriodMonthDesc(employeeId);
        return result.stream().map(this::toResponse).toList();
    }
    public PayslipResponse create(PayslipCreate request) {
        Employee employee = employees.findById(request.employeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        if (payslips.existsByEmployeeIdAndPeriodYearAndPeriodMonth(request.employeeId(), request.periodYear(), request.periodMonth()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A payslip already exists for this employee and period");
        BigDecimal net = request.grossSalary().add(request.additions()).subtract(request.deductions());
        if (net.signum() < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deductions cannot exceed gross salary plus additions");
        return toResponse(payslips.save(new Payslip(employee, request.periodYear(), request.periodMonth(), request.grossSalary(), request.additions(), request.deductions())));
    }
    private PayslipResponse toResponse(Payslip p) {
        return new PayslipResponse(p.getId(), p.getEmployee().getId(), p.getEmployee().getFirstName() + " " + p.getEmployee().getLastName(),
                p.getPeriodYear(), p.getPeriodMonth(), p.getGrossSalary(), p.getAdditions(), p.getDeductions(), p.getNetSalary());
    }
}
