package ma.atlasrh.service;

import ma.atlasrh.dto.TrainingCourseRequest;
import ma.atlasrh.dto.TrainingCourseResponse;
import ma.atlasrh.entity.TrainingCourse;
import ma.atlasrh.repository.TrainingCourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class TrainingCourseService {
    private final TrainingCourseRepository courses;
    public TrainingCourseService(TrainingCourseRepository courses) { this.courses = courses; }
    @Transactional(readOnly = true)
    public List<TrainingCourseResponse> findAll() { return courses.findAll().stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true)
    public TrainingCourseResponse findById(Long id) { return toResponse(get(id)); }
    public TrainingCourseResponse create(TrainingCourseRequest request) {
        return toResponse(courses.save(new TrainingCourse(request.title(), request.description(), request.provider(), request.durationHours())));
    }
    public TrainingCourseResponse update(Long id, TrainingCourseRequest request) {
        TrainingCourse existing = get(id);
        existing.updateDetails(request.title(), request.description(), request.provider(), request.durationHours());
        return toResponse(courses.save(existing));
    }
    public void delete(Long id) { courses.delete(get(id)); }
    private TrainingCourse get(Long id) { return courses.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training course not found")); }
    private TrainingCourseResponse toResponse(TrainingCourse c) { return new TrainingCourseResponse(c.getId(), c.getTitle(), c.getDescription(), c.getProvider(), c.getDurationHours()); }
}
