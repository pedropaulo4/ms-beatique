package br.com.beautique.api.controllers;

import br.com.beautique.api.dtos.AppointmentDTO;
import br.com.beautique.api.services.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentsService.create(appointmentDTO));
    }

    @PatchMapping
    public ResponseEntity<AppointmentDTO> update(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentsService.update(appointmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<AppointmentDTO> setCustomerToAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentsService.setCustomerToAppointment(appointmentDTO));
    }
}
