package ma.atlasrh.repository;

import ma.atlasrh.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);
    List<AttendanceRecord> findByEmployeeIdAndWorkDateBetweenOrderByWorkDateDesc(Long employeeId, LocalDate from, LocalDate to);
    List<AttendanceRecord> findByWorkDateBetweenOrderByWorkDateDesc(LocalDate from, LocalDate to);
}
