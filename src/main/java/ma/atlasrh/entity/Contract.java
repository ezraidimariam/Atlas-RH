package ma.atlasrh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractType type;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    // Can be empty for an ongoing CDI contract.
    private LocalDate endDate;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal grossSalary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    protected Contract() {
        // Required by JPA.
    }

    public Contract(ContractType type, LocalDate startDate, LocalDate endDate,
                    BigDecimal grossSalary, Employee employee) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grossSalary = grossSalary;
        this.employee = employee;
    }

    public Long getId() { return id; }
    public ContractType getType() { return type; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public Employee getEmployee() { return employee; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
