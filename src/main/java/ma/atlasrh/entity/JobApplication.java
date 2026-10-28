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
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, length = 150)
    private String email;
    @Column(length = 30)
    private String phone;
    @Column(name = "resume_url", length = 1000)
    private String resumeUrl;
    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status = ApplicationStatus.RECEIVED;

    protected JobApplication() {}
    public JobApplication(JobOpening jobOpening, String fullName, String email, String phone, String resumeUrl) {
        this.jobOpening = jobOpening;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.appliedAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public JobOpening getJobOpening() { return jobOpening; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getResumeUrl() { return resumeUrl; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}
