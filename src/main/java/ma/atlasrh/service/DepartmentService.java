package ma.atlasrh.service;

import ma.atlasrh.dto.DepartmentRequest;
import ma.atlasrh.dto.DepartmentResponse;
import ma.atlasrh.entity.Department;
import ma.atlasrh.repository.DepartmentRepository;
import ma.atlasrh.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository repository;
    private final EmployeeRepository employees;

    public DepartmentService(DepartmentRepository repository, EmployeeRepository employees) {
        this.repository = repository;
        this.employees = employees;
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() { return repository.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) { return toResponse(get(id)); }

    public DepartmentResponse create(DepartmentRequest request) {
        if (repository.existsByNameIgnoreCase(request.name())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Department name already exists");
        return toResponse(repository.save(new Department(request.name(), request.description())));
    }

    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = get(id);
        if (repository.existsByNameIgnoreCase(request.name()) && !department.getName().equalsIgnoreCase(request.name()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department name already exists");
        department.setName(request.name());
        department.setDescription(request.description());
        return toResponse(repository.save(department));
    }

    public void delete(Long id) {
        Department department = get(id);
        if (employees.existsByDepartmentId(id)) throw new ResponseStatusException(HttpStatus.CONFLICT, "Department still has employees");
        repository.delete(department);
    }

    private Department get(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")); }
    private DepartmentResponse toResponse(Department d) { return new DepartmentResponse(d.getId(), d.getName(), d.getDescription()); }
}
