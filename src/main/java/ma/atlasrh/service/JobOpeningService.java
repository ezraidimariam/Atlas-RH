package ma.atlasrh.service;

import ma.atlasrh.dto.JobOpeningRequest;
import ma.atlasrh.dto.JobOpeningResponse;
import ma.atlasrh.entity.Department;
import ma.atlasrh.entity.JobOpening;
import ma.atlasrh.entity.JobOpeningStatus;
import ma.atlasrh.repository.DepartmentRepository;
import ma.atlasrh.repository.JobOpeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class JobOpeningService {
    private final JobOpeningRepository openings;
    private final DepartmentRepository departments;
    public JobOpeningService(JobOpeningRepository openings, DepartmentRepository departments) { this.openings = openings; this.departments = departments; }

    @Transactional(readOnly = true)
    public List<JobOpeningResponse> find(JobOpeningStatus status) {
        List<JobOpening> result = status == null ? openings.findAll() : openings.findByStatusOrderByOpenedOnDesc(status);
        return result.stream().map(this::toResponse).toList();
    }
    @Transactional(readOnly = true)
    public JobOpeningResponse findById(Long id) { return toResponse(get(id)); }
    public JobOpeningResponse create(JobOpeningRequest request) {
        validate(request);
        Department department = departments.findById(request.departmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        return toResponse(openings.save(new JobOpening(request.title(), request.description(), department, request.openedOn(), request.closesOn())));
    }
    public JobOpeningResponse setStatus(Long id, JobOpeningStatus status) {
        JobOpening opening = get(id);
        opening.setStatus(status);
        return toResponse(openings.save(opening));
    }
    private void validate(JobOpeningRequest request) {
        if (request.closesOn() != null && request.closesOn().isBefore(request.openedOn())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Closing date cannot be before opening date");
    }
    private JobOpening get(Long id) { return openings.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job opening not found")); }
    private JobOpeningResponse toResponse(JobOpening j) { return new JobOpeningResponse(j.getId(), j.getTitle(), j.getDescription(), j.getDepartment().getId(), j.getDepartment().getName(), j.getOpenedOn(), j.getClosesOn(), j.getStatus()); }
}
