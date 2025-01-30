package br.com.ms_beautique_query.controllers;

import br.com.ms_beautique_query.dtos.appointments.FullAppointmentDTO;
import br.com.ms_beautique_query.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/appointment")
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    ResponseEntity<List<FullAppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.listAllAppointments());
    }

    @GetMapping("/customer/{customerId}")
    ResponseEntity<List<FullAppointmentDTO>> getAllAppointmentsByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(appointmentService.listAppointmentsByCustomer(customerId));
    }

    @GetMapping("/beauty-procedure/{beautyProcedureId}")
    ResponseEntity<List<FullAppointmentDTO>> getAllAppointmentsByBeautyProcedureId(@PathVariable("beautyProcedureId") Long beautyProcedureId) {
        return ResponseEntity.ok(appointmentService.listAppointmentsByBeautyProcedure(beautyProcedureId));
    }
}
