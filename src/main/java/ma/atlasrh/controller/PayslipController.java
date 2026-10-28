package ma.atlasrh.controller;

import jakarta.validation.Valid;
import ma.atlasrh.dto.PayslipCreate;
import ma.atlasrh.dto.PayslipResponse;
import ma.atlasrh.service.PayslipService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/payslips")
public class PayslipController {
    private final PayslipService service;
    public PayslipController(PayslipService service) { this.service = service; }
    @GetMapping public List<PayslipResponse> find(@RequestParam(required = false) Long employeeId) { return service.find(employeeId); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public PayslipResponse create(@Valid @RequestBody PayslipCreate request) { return service.create(request); }
}
