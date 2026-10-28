package ma.atlasrh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "payslips", uniqueConstraints = @UniqueConstraint(name = "uk_payslip_employee_period", columnNames = {"employee_id", "period_year", "period_month"}))
public class Payslip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @Column(name = "period_year", nullable = false)
    private int periodYear;
    @Column(name = "period_month", nullable = false)
    private int periodMonth;
    @Column(name = "gross_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal grossSalary;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal additions;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deductions;
    @Column(name = "net_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal netSalary;

    protected Payslip() {}
    public Payslip(Employee employee, int periodYear, int periodMonth, BigDecimal grossSalary, BigDecimal additions, BigDecimal deductions) {
        this.employee = employee;
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.grossSalary = grossSalary;
        this.additions = additions;
        this.deductions = deductions;
        this.netSalary = grossSalary.add(additions).subtract(deductions).setScale(2, RoundingMode.HALF_UP);
    }
    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public int getPeriodYear() { return periodYear; }
    public int getPeriodMonth() { return periodMonth; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public BigDecimal getAdditions() { return additions; }
    public BigDecimal getDeductions() { return deductions; }
    public BigDecimal getNetSalary() { return netSalary; }
}
