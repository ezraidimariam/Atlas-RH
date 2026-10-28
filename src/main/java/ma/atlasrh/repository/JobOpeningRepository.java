package ma.atlasrh.repository;

import ma.atlasrh.entity.JobOpening;
import ma.atlasrh.entity.JobOpeningStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
    List<JobOpening> findByStatusOrderByOpenedOnDesc(JobOpeningStatus status);
}
