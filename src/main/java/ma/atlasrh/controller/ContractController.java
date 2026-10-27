package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.ContractRequest;
import ma.atlasrh.dto.ContractResponse;
import ma.atlasrh.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractService service;
    public ContractController(ContractService service) { this.service = service; }

    @GetMapping public List<ContractResponse> findAll(@RequestParam(required = false) Long employeeId) { return service.findAll(employeeId); }
    @GetMapping("/{id}") public ContractResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse create(@Valid @RequestBody ContractRequest request) { return service.create(request); }
    @PutMapping("/{id}") public ContractResponse update(@PathVariable Long id, @Valid @RequestBody ContractRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
