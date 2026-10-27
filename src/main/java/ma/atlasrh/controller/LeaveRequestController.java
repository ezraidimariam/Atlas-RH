package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.LeaveRequestCreate;
import ma.atlasrh.dto.LeaveRequestResponse;
import ma.atlasrh.entity.LeaveStatus;
import ma.atlasrh.service.LeaveRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {
    private final LeaveRequestService service;
    public LeaveRequestController(LeaveRequestService service) { this.service = service; }

    @GetMapping
    public List<LeaveRequestResponse> findAll(@RequestParam(required = false) Long employeeId,
                                              @RequestParam(required = false) LeaveStatus status) {
        return service.findAll(employeeId, status);
    }
    @GetMapping("/{id}") public LeaveRequestResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public LeaveRequestResponse create(@Valid @RequestBody LeaveRequestCreate request) { return service.create(request); }
    @PostMapping("/{id}/approve") public LeaveRequestResponse approve(@PathVariable Long id) { return service.decide(id, true); }
    @PostMapping("/{id}/reject") public LeaveRequestResponse reject(@PathVariable Long id) { return service.decide(id, false); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
