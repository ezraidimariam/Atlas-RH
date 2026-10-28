package ma.atlasrh.repository;

import ma.atlasrh.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobOpeningIdOrderByAppliedAtDesc(Long jobOpeningId);
}
