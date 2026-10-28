package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.JobOpeningRequest;
import ma.atlasrh.dto.JobOpeningResponse;
import ma.atlasrh.entity.JobOpeningStatus;
import ma.atlasrh.service.JobOpeningService;
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
@RequestMapping("/api/job-openings")
public class JobOpeningController {
    private final JobOpeningService service;
    public JobOpeningController(JobOpeningService service) { this.service = service; }
    @GetMapping public List<JobOpeningResponse> find(@RequestParam(required = false) JobOpeningStatus status) { return service.find(status); }
    @GetMapping("/{id}") public JobOpeningResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public JobOpeningResponse create(@Valid @RequestBody JobOpeningRequest request) { return service.create(request); }
    @PutMapping("/{id}/status")
    public JobOpeningResponse setStatus(@PathVariable Long id, @RequestParam JobOpeningStatus status) { return service.setStatus(id, status); }
}
