package br.com.ms_beautique_query.services;

import br.com.ms_beautique_query.dtos.appointments.FullAppointmentDTO;

import java.util.List;

public interface AppointmentService {

    List<FullAppointmentDTO> listAllAppointments();
    List<FullAppointmentDTO> listAppointmentsByCustomer(Long id);
    List<FullAppointmentDTO> listAppointmentsByBeautyProcedure(Long id);
}
