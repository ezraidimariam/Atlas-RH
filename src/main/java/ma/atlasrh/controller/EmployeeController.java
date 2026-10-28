package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.EmployeeRequest;
import ma.atlasrh.dto.EmployeeResponse;
import ma.atlasrh.service.EmployeeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }

    @GetMapping public List<EmployeeResponse> findAll() { return service.findAll(); }
    @GetMapping("/search")
    public Page<EmployeeResponse> search(@RequestParam(required = false) String q,
                                         @RequestParam(required = false) Long departmentId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return service.search(q, departmentId, page, size);
    }
    @GetMapping("/{id}") public EmployeeResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request) { return service.create(request); }
    @PutMapping("/{id}") public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) { return service.update(id, request); }
    @PutMapping("/{id}/status")
    public EmployeeResponse setStatus(@PathVariable Long id, @RequestParam boolean active) { return service.setActive(id, active); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
