package ma.atlasrh.service;

import ma.atlasrh.dto.AttendanceResponse;
import ma.atlasrh.entity.AttendanceRecord;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.repository.AttendanceRecordRepository;
import ma.atlasrh.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceRecordRepository records;
    private final EmployeeRepository employees;

    public AttendanceService(AttendanceRecordRepository records, EmployeeRepository employees) {
        this.records = records;
        this.employees = employees;
    }

    public AttendanceResponse clockIn(Long employeeId) {
        Employee employee = getEmployee(employeeId);
        if (!employee.isActive()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Inactive employees cannot clock in");
        LocalDateTime now = LocalDateTime.now();
        if (records.findByEmployeeIdAndWorkDate(employeeId, now.toLocalDate()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already clocked in today");
        return toResponse(records.save(new AttendanceRecord(employee, now)));
    }

    public AttendanceResponse clockOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        AttendanceRecord record = records.findByEmployeeIdAndWorkDate(employeeId, today)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No clock-in found for today"));
        if (record.getClockOut() != null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already clocked out today");
        record.setClockOut(LocalDateTime.now());
        return toResponse(records.save(record));
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponse> find(Long employeeId, LocalDate from, LocalDate to) {
        if (from == null || to == null || to.isBefore(from)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A valid from and to date range is required");
        List<AttendanceRecord> result = employeeId == null
                ? records.findByWorkDateBetweenOrderByWorkDateDesc(from, to)
                : records.findByEmployeeIdAndWorkDateBetweenOrderByWorkDateDesc(employeeId, from, to);
        return result.stream().map(this::toResponse).toList();
    }

    private Employee getEmployee(Long id) { return employees.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")); }
    private AttendanceResponse toResponse(AttendanceRecord r) {
        Employee e = r.getEmployee();
        return new AttendanceResponse(r.getId(), e.getId(), e.getEmployeeNumber(), e.getFirstName() + " " + e.getLastName(),
                r.getWorkDate(), r.getClockIn(), r.getClockOut());
    }
}
