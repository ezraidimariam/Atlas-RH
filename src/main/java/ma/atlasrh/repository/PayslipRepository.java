package ma.atlasrh.repository;

import ma.atlasrh.entity.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    List<Payslip> findByEmployeeIdOrderByPeriodYearDescPeriodMonthDesc(Long employeeId);
    boolean existsByEmployeeIdAndPeriodYearAndPeriodMonth(Long employeeId, int periodYear, int periodMonth);
}
