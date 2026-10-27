package ma.atlasrh.service;

import ma.atlasrh.dto.ContractRequest;
import ma.atlasrh.dto.ContractResponse;
import ma.atlasrh.entity.Contract;
import ma.atlasrh.entity.Employee;
import ma.atlasrh.repository.ContractRepository;
import ma.atlasrh.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Transactional
public class ContractService {
    private final ContractRepository contracts;
    private final EmployeeRepository employees;

    public ContractService(ContractRepository contracts, EmployeeRepository employees) { this.contracts = contracts; this.employees = employees; }

    @Transactional(readOnly = true)
    public List<ContractResponse> findAll(Long employeeId) {
        List<Contract> result = employeeId == null ? contracts.findAll() : contracts.findByEmployeeId(employeeId);
        return result.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ContractResponse findById(Long id) { return toResponse(get(id)); }

    public ContractResponse create(ContractRequest request) {
        validateDates(request);
        return toResponse(contracts.save(new Contract(request.type(), request.startDate(), request.endDate(), request.grossSalary(), employee(request.employeeId()))));
    }

    public ContractResponse update(Long id, ContractRequest request) {
        validateDates(request);
        Contract contract = get(id);
        contract.setDetails(request.type(), request.startDate(), request.endDate(), request.grossSalary(), employee(request.employeeId()));
        return toResponse(contracts.save(contract));
    }

    public void delete(Long id) { contracts.delete(get(id)); }

    private void validateDates(ContractRequest request) {
        if (request.endDate() != null && request.endDate().isBefore(request.startDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contract end date cannot be before start date");
    }
    private Contract get(Long id) { return contracts.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract not found")); }
    private Employee employee(Long id) { return employees.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")); }
    private ContractResponse toResponse(Contract c) { return new ContractResponse(c.getId(), c.getType(), c.getStartDate(), c.getEndDate(), c.getGrossSalary(), c.getEmployee().getId()); }
}
