package ma.atlasrh.repository;

import ma.atlasrh.entity.LeaveRequest;
import ma.atlasrh.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    boolean existsByEmployeeId(Long employeeId);
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatus(LeaveStatus status);
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);

    @Query("select count(l) > 0 from LeaveRequest l where l.employee.id = :employeeId and l.status = :status and l.startDate <= :endDate and l.endDate >= :startDate")
    boolean hasOverlap(@Param("employeeId") Long employeeId, @Param("status") LeaveStatus status,
                       @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
