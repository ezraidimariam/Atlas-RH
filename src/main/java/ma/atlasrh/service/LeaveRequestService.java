package ma.atlasrh.service;

import ma.atlasrh.dto.LeaveRequestCreate;
import ma.atlasrh.dto.LeaveRequestResponse;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.entity.LeaveRequest;
import ma.atlasrh.entity.LeaveStatus;
import ma.atlasrh.repository.EmployeeRepository;
import ma.atlasrh.repository.LeaveRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class LeaveRequestService {
    private final LeaveRequestRepository leaves;
    private final EmployeeRepository employees;

    public LeaveRequestService(LeaveRequestRepository leaves, EmployeeRepository employees) {
        this.leaves = leaves;
        this.employees = employees;
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestResponse> findAll(Long employeeId, LeaveStatus status) {
        List<LeaveRequest> result;
        if (employeeId != null && status != null) result = leaves.findByEmployeeIdAndStatus(employeeId, status);
        else if (employeeId != null) result = leaves.findByEmployeeId(employeeId);
        else if (status != null) result = leaves.findByStatus(status);
        else result = leaves.findAll();
        return result.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LeaveRequestResponse findById(Long id) { return toResponse(get(id)); }

    public LeaveRequestResponse create(LeaveRequestCreate request) {
        if (request.endDate().isBefore(request.startDate())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Leave end date cannot be before start date");
        Employee employee = employees.findById(request.employeeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        if (leaves.hasOverlap(employee.getId(), LeaveStatus.APPROVED, request.startDate(), request.endDate()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Leave overlaps an already approved leave");
        return toResponse(leaves.save(new LeaveRequest(employee, request.startDate(), request.endDate(), request.reason())));
    }

    public LeaveRequestResponse decide(Long id, boolean approve) {
        LeaveRequest request = get(id);
        if (request.getStatus() != LeaveStatus.PENDING) throw new ResponseStatusException(HttpStatus.CONFLICT, "Only pending leave requests can be decided");
        if (approve && leaves.hasOverlap(request.getEmployee().getId(), LeaveStatus.APPROVED, request.getStartDate(), request.getEndDate()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Leave overlaps an already approved leave");
        request.setStatus(approve ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
        return toResponse(leaves.save(request));
    }

    public void delete(Long id) {
        LeaveRequest request = get(id);
        if (request.getStatus() != LeaveStatus.PENDING) throw new ResponseStatusException(HttpStatus.CONFLICT, "Only pending leave requests can be deleted");
        leaves.delete(request);
    }

    private LeaveRequest get(Long id) { return leaves.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found")); }
    private LeaveRequestResponse toResponse(LeaveRequest l) {
        return new LeaveRequestResponse(l.getId(), l.getEmployee().getId(), l.getEmployee().getEmployeeNumber(),
                l.getEmployee().getFirstName() + " " + l.getEmployee().getLastName(), l.getStartDate(), l.getEndDate(), l.getReason(), l.getStatus());
    }
}
