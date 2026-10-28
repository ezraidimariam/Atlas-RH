package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.TrainingCourseRequest;
import ma.atlasrh.dto.TrainingCourseResponse;
import ma.atlasrh.service.TrainingCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/training-courses")
public class TrainingCourseController {
    private final TrainingCourseService service;
    public TrainingCourseController(TrainingCourseService service) { this.service = service; }
    @GetMapping public List<TrainingCourseResponse> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public TrainingCourseResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public TrainingCourseResponse create(@Valid @RequestBody TrainingCourseRequest request) { return service.create(request); }
    @PutMapping("/{id}") public TrainingCourseResponse update(@PathVariable Long id, @Valid @RequestBody TrainingCourseRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
