package kz.iitu.clinic.controller;

import kz.iitu.clinic.model.Appointment;
import kz.iitu.clinic.service.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class ClinicController {

    private final ClinicService service;

    public ClinicController(ClinicService service) {
        this.service = service;
    }

    @GetMapping
    public List<Appointment> getAll(@RequestParam(required = false) String search) {
        return service.getAll(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        if (appointment.getPatientName() == null || appointment.getPatientName().isBlank() ||
            appointment.getPhone() == null || appointment.getPhone().isBlank() ||
            appointment.getDoctorName() == null || appointment.getDoctorName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Appointment created = service.create(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody Appointment details) {
        if (details.getPatientName() == null || details.getPatientName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return service.update(id, details)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
