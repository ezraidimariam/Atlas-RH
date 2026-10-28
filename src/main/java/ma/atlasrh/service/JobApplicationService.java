package ma.atlasrh.service;

import ma.atlasrh.dto.JobApplicationCreate;
import ma.atlasrh.dto.JobApplicationResponse;
import ma.atlasrh.entity.ApplicationStatus;
import ma.atlasrh.entity.JobApplication;
import ma.atlasrh.entity.JobOpening;
import ma.atlasrh.entity.JobOpeningStatus;
import ma.atlasrh.repository.JobApplicationRepository;
import ma.atlasrh.repository.JobOpeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class JobApplicationService {
    private final JobApplicationRepository applications;
    private final JobOpeningRepository openings;
    public JobApplicationService(JobApplicationRepository applications, JobOpeningRepository openings) { this.applications = applications; this.openings = openings; }

    @Transactional(readOnly = true)
    public List<JobApplicationResponse> findByOpening(Long openingId) {
        if (!openings.existsById(openingId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job opening not found");
        return applications.findByJobOpeningIdOrderByAppliedAtDesc(openingId).stream().map(this::toResponse).toList();
    }
    public JobApplicationResponse apply(Long openingId, JobApplicationCreate request) {
        JobOpening opening = openings.findById(openingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job opening not found"));
        if (opening.getStatus() != JobOpeningStatus.OPEN) throw new ResponseStatusException(HttpStatus.CONFLICT, "Job opening is closed");
        return toResponse(applications.save(new JobApplication(opening, request.fullName(), request.email(), request.phone(), request.resumeUrl())));
    }
    public JobApplicationResponse updateStatus(Long id, ApplicationStatus status) {
        JobApplication application = applications.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        application.setStatus(status);
        return toResponse(applications.save(application));
    }
    private JobApplicationResponse toResponse(JobApplication a) { return new JobApplicationResponse(a.getId(), a.getJobOpening().getId(), a.getFullName(), a.getEmail(), a.getPhone(), a.getResumeUrl(), a.getAppliedAt(), a.getStatus()); }
}
