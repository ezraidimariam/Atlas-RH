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
import java.time.LocalDate;

@Entity
@Table(name = "job_openings")
public class JobOpening {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(nullable = false, length = 4000)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @Column(name = "opened_on", nullable = false)
    private LocalDate openedOn;
    @Column(name = "closes_on")
    private LocalDate closesOn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobOpeningStatus status = JobOpeningStatus.OPEN;

    protected JobOpening() {}
    public JobOpening(String title, String description, Department department, LocalDate openedOn, LocalDate closesOn) {
        this.title = title;
        this.description = description;
        this.department = department;
        this.openedOn = openedOn;
        this.closesOn = closesOn;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Department getDepartment() { return department; }
    public LocalDate getOpenedOn() { return openedOn; }
    public LocalDate getClosesOn() { return closesOn; }
    public JobOpeningStatus getStatus() { return status; }
    public void setStatus(JobOpeningStatus status) { this.status = status; }
}
