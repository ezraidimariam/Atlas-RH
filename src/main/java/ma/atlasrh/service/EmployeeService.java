package ma.atlasrh.service;

import ma.atlasrh.dto.EmployeeRequest;
import ma.atlasrh.dto.EmployeeResponse;
import ma.atlasrh.entity.Department;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.repository.DepartmentRepository;
import ma.atlasrh.repository.EmployeeRepository;
import ma.atlasrh.repository.ContractRepository;
import ma.atlasrh.repository.LeaveRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;
    private final ContractRepository contracts;
    private final LeaveRequestRepository leaveRequests;

    public EmployeeService(EmployeeRepository employees, DepartmentRepository departments,
                           ContractRepository contracts, LeaveRequestRepository leaveRequests) {
        this.employees = employees;
        this.departments = departments;
        this.contracts = contracts;
        this.leaveRequests = leaveRequests;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() { return employees.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) { return toResponse(get(id)); }

    public EmployeeResponse create(EmployeeRequest request) {
        if (employees.existsByEmployeeNumber(request.employeeNumber())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee number already exists");
        if (employees.existsByEmailIgnoreCase(request.email())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        Employee manager = request.managerId() == null ? null : get(request.managerId());
        Employee employee = new Employee(request.employeeNumber(), request.lastName(), request.firstName(), request.email(),
                request.jobTitle(), request.hireDate(), department(request.departmentId()), manager);
        return toResponse(employees.save(employee));
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = get(id);
        if (request.employeeNumber() != null && !employee.getEmployeeNumber().equals(request.employeeNumber()) && employees.existsByEmployeeNumber(request.employeeNumber()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee number already exists");
        if (request.email() != null && !employee.getEmail().equalsIgnoreCase(request.email()) && employees.existsByEmailIgnoreCase(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        if (request.managerId() != null && request.managerId().equals(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An employee cannot manage themselves");
        Employee manager = request.managerId() == null ? null : get(request.managerId());
        for (Employee ancestor = manager; ancestor != null; ancestor = ancestor.getManager()) {
            if (ancestor.getId().equals(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager assignment would create a reporting cycle");
        }
        employee.setEmployeeNumber(request.employeeNumber());
        employee.setLastName(request.lastName());
        employee.setFirstName(request.firstName());
        employee.setEmail(request.email());
        employee.setJobTitle(request.jobTitle());
        employee.setHireDate(request.hireDate());
        employee.setDepartment(department(request.departmentId()));
        employee.setManager(manager);
        return toResponse(employees.save(employee));
    }

    public void delete(Long id) {
        Employee employee = get(id);
        if (employees.existsByManagerId(id) || contracts.existsByEmployeeId(id) || leaveRequests.existsByEmployeeId(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee has linked records and cannot be deleted");
        employees.delete(employee);
    }

    private Employee get(Long id) { return employees.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")); }
    private Department department(Long id) { return departments.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")); }
    private EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(e.getId(), e.getEmployeeNumber(), e.getLastName(), e.getFirstName(), e.getEmail(), e.getJobTitle(),
                e.getHireDate(), e.getDepartment().getId(), e.getDepartment().getName(), e.getManager() == null ? null : e.getManager().getId());
    }
}
