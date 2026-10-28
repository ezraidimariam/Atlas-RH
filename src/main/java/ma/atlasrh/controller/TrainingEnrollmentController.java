package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.TrainingEnrollmentCreate;
import ma.atlasrh.dto.TrainingEnrollmentResponse;
import ma.atlasrh.service.TrainingEnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/training-enrollments")
public class TrainingEnrollmentController {
    private final TrainingEnrollmentService service;
    public TrainingEnrollmentController(TrainingEnrollmentService service) { this.service = service; }
    @GetMapping("/employee/{employeeId}")
    public List<TrainingEnrollmentResponse> findByEmployee(@PathVariable Long employeeId) { return service.findByEmployee(employeeId); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public TrainingEnrollmentResponse enroll(@Valid @RequestBody TrainingEnrollmentCreate request) { return service.enroll(request); }
    @PostMapping("/{id}/complete")
    public TrainingEnrollmentResponse complete(@PathVariable Long id) { return service.complete(id); }
    @PostMapping("/{id}/cancel")
    public TrainingEnrollmentResponse cancel(@PathVariable Long id) { return service.cancel(id); }
}
