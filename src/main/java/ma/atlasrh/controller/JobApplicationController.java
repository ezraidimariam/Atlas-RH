package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.JobApplicationCreate;
import ma.atlasrh.dto.JobApplicationResponse;
import ma.atlasrh.entity.ApplicationStatus;
import ma.atlasrh.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JobApplicationController {
    private final JobApplicationService service;
    public JobApplicationController(JobApplicationService service) { this.service = service; }
    @GetMapping("/job-openings/{openingId}/applications")
    public List<JobApplicationResponse> findByOpening(@PathVariable Long openingId) { return service.findByOpening(openingId); }
    @PostMapping("/job-openings/{openingId}/applications") @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationResponse apply(@PathVariable Long openingId, @Valid @RequestBody JobApplicationCreate request) { return service.apply(openingId, request); }
    @PutMapping("/job-applications/{id}/status")
    public JobApplicationResponse updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) { return service.updateStatus(id, status); }
}
