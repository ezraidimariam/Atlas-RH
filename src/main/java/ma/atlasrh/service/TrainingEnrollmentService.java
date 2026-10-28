package ma.atlasrh.service;

import ma.atlasrh.dto.TrainingEnrollmentCreate;
import ma.atlasrh.dto.TrainingEnrollmentResponse;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.entity.TrainingCourse;
import ma.atlasrh.entity.TrainingEnrollment;
import ma.atlasrh.entity.TrainingStatus;
import ma.atlasrh.repository.EmployeeRepository;
import ma.atlasrh.repository.TrainingCourseRepository;
import ma.atlasrh.repository.TrainingEnrollmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class TrainingEnrollmentService {
    private final TrainingEnrollmentRepository enrollments;
    private final EmployeeRepository employees;
    private final TrainingCourseRepository courses;
    public TrainingEnrollmentService(TrainingEnrollmentRepository enrollments, EmployeeRepository employees, TrainingCourseRepository courses) {
        this.enrollments = enrollments; this.employees = employees; this.courses = courses;
    }
    @Transactional(readOnly = true)
    public List<TrainingEnrollmentResponse> findByEmployee(Long employeeId) {
        if (!employees.existsById(employeeId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return enrollments.findByEmployeeIdOrderByEnrolledOnDesc(employeeId).stream().map(this::toResponse).toList();
    }
    public TrainingEnrollmentResponse enroll(TrainingEnrollmentCreate request) {
        Employee employee = employees.findById(request.employeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        if (!employee.isActive()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Inactive employees cannot enroll");
        TrainingCourse course = courses.findById(request.courseId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training course not found"));
        if (enrollments.existsByEmployeeIdAndCourseIdAndStatus(employee.getId(), course.getId(), TrainingStatus.ENROLLED))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee is already enrolled");
        return toResponse(enrollments.save(new TrainingEnrollment(employee, course)));
    }
    public TrainingEnrollmentResponse complete(Long id) {
        TrainingEnrollment enrollment = get(id);
        if (enrollment.getStatus() != TrainingStatus.ENROLLED) throw new ResponseStatusException(HttpStatus.CONFLICT, "Only active enrollments can be completed");
        enrollment.complete();
        return toResponse(enrollments.save(enrollment));
    }
    public TrainingEnrollmentResponse cancel(Long id) {
        TrainingEnrollment enrollment = get(id);
        if (enrollment.getStatus() != TrainingStatus.ENROLLED) throw new ResponseStatusException(HttpStatus.CONFLICT, "Only active enrollments can be cancelled");
        enrollment.cancel();
        return toResponse(enrollments.save(enrollment));
    }
    private TrainingEnrollment get(Long id) { return enrollments.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training enrollment not found")); }
    private TrainingEnrollmentResponse toResponse(TrainingEnrollment e) {
        return new TrainingEnrollmentResponse(e.getId(), e.getEmployee().getId(), e.getEmployee().getFirstName() + " " + e.getEmployee().getLastName(),
                e.getCourse().getId(), e.getCourse().getTitle(), e.getEnrolledOn(), e.getCompletedOn(), e.getStatus());
    }
}
