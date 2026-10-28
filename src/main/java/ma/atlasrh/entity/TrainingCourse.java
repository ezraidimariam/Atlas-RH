package ma.atlasrh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "training_courses")
public class TrainingCourse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(length = 4000)
    private String description;
    @Column(length = 150)
    private String provider;
    @Column(name = "duration_hours", nullable = false, precision = 6, scale = 2)
    private BigDecimal durationHours;

    protected TrainingCourse() {}
    public TrainingCourse(String title, String description, String provider, BigDecimal durationHours) {
        this.title = title;
        this.description = description;
        this.provider = provider;
        this.durationHours = durationHours;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getProvider() { return provider; }
    public BigDecimal getDurationHours() { return durationHours; }
    public void updateDetails(String title, String description, String provider, BigDecimal durationHours) {
        this.title = title;
        this.description = description;
        this.provider = provider;
        this.durationHours = durationHours;
    }
}
