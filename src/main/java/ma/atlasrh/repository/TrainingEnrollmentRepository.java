package ma.atlasrh.repository;

import ma.atlasrh.entity.TrainingEnrollment;
import ma.atlasrh.entity.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, Long> {
    List<TrainingEnrollment> findByEmployeeIdOrderByEnrolledOnDesc(Long employeeId);
    boolean existsByEmployeeIdAndCourseIdAndStatus(Long employeeId, Long courseId, TrainingStatus status);
}
