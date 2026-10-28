package ma.atlasrh.controller;

import ma.atlasrh.dto.AttendanceResponse;
import ma.atlasrh.dto.AttendanceSummary;
import ma.atlasrh.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service = service; }

    @PostMapping("/{employeeId}/clock-in") @ResponseStatus(HttpStatus.CREATED)
    public AttendanceResponse clockIn(@PathVariable Long employeeId) { return service.clockIn(employeeId); }
    @PostMapping("/{employeeId}/clock-out")
    public AttendanceResponse clockOut(@PathVariable Long employeeId) { return service.clockOut(employeeId); }
    @GetMapping
    public List<AttendanceResponse> find(@RequestParam(required = false) Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.find(employeeId, from, to);
    }

    @GetMapping("/summary")
    public AttendanceSummary summarize(@RequestParam(required = false) Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.summarize(employeeId, from, to);
    }
}
