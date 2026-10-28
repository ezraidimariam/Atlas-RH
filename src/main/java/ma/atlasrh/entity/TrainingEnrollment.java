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
@Table(name = "training_enrollments")
public class TrainingEnrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private TrainingCourse course;
    @Column(name = "enrolled_on", nullable = false)
    private LocalDate enrolledOn;
    @Column(name = "completed_on")
    private LocalDate completedOn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TrainingStatus status = TrainingStatus.ENROLLED;

    protected TrainingEnrollment() {}
    public TrainingEnrollment(Employee employee, TrainingCourse course) {
        this.employee = employee;
        this.course = course;
        this.enrolledOn = LocalDate.now();
    }
    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public TrainingCourse getCourse() { return course; }
    public LocalDate getEnrolledOn() { return enrolledOn; }
    public LocalDate getCompletedOn() { return completedOn; }
    public TrainingStatus getStatus() { return status; }
    public void complete() { this.status = TrainingStatus.COMPLETED; this.completedOn = LocalDate.now(); }
    public void cancel() { this.status = TrainingStatus.CANCELLED; }
}
