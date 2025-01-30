package br.com.ms_beautique_query.services.impl;

import br.com.ms_beautique_query.dtos.appointments.FullAppointmentDTO;
import br.com.ms_beautique_query.repositories.AppointmentRepository;
import br.com.ms_beautique_query.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<FullAppointmentDTO> listAllAppointments() {
        try {
            return appointmentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error listing all appointments");
        }
    }

    @Override
    public List<FullAppointmentDTO> listAppointmentsByCustomer(Long id) {
        try {
            return appointmentRepository.listAppointmentByCustomerId(id);
        } catch (Exception e) {
            throw new RuntimeException("Error listing all appointments by customer");
        }
    }

    @Override
    public List<FullAppointmentDTO> listAppointmentsByBeautyProcedure(Long id) {
        try {
            return appointmentRepository.listAppointmentByBeautyProcedureId(id);
        } catch (Exception e) {
            throw new RuntimeException("Error listing all appointments by beauty procedure");
        }
    }
}
