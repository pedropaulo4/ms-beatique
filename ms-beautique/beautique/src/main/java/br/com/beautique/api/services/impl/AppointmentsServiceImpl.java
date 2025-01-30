package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.AppointmentDTO;
import br.com.beautique.api.dtos.BeautyProcedureDTO;
import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.dtos.FullAppointmentDTO;
import br.com.beautique.api.entities.AppointmentsEntity;
import br.com.beautique.api.entities.BeautyProceduresEntity;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.repositories.AppointmentRepository;
import br.com.beautique.api.repositories.BeautyProcedureRepository;
import br.com.beautique.api.repositories.CustomerRepository;
import br.com.beautique.api.services.AppointmentsService;
import br.com.beautique.api.services.BrokerService;
import br.com.beautique.api.utils.ConvertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final ConvertUtil<AppointmentsEntity, AppointmentDTO> convertUtil = new ConvertUtil<>(AppointmentsEntity.class, AppointmentDTO.class);

    @Autowired
    private BeautyProcedureRepository beautyProcedureRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private BrokerService  brokerService;

    @Override
    public AppointmentDTO create(AppointmentDTO appointmentDTO) {
        AppointmentsEntity appointmentsEntity = convertUtil.convertToSource(appointmentDTO);
        AppointmentsEntity savedAppointmentsEntity = appointmentRepository.save(appointmentsEntity);
        sendAppointmentToQueue(savedAppointmentsEntity);
        return convertUtil.convertToTarget(savedAppointmentsEntity);
    }

    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) {
        Optional<AppointmentsEntity> currentAppointment = appointmentRepository.findById(appointmentDTO.getId());
        if(currentAppointment.isEmpty()) {
            throw new RuntimeException("Appointment not found");
        }
        AppointmentsEntity appointmentEntity = convertUtil.convertToSource(appointmentDTO);
        appointmentEntity.setCreatedAt(currentAppointment.get().getCreatedAt());
        AppointmentsEntity updateAppointmentEntity = appointmentRepository.save(appointmentEntity);
        sendAppointmentToQueue(updateAppointmentEntity);
        return convertUtil.convertToTarget(updateAppointmentEntity);

    }

    @Override
    public void deleteById(Long id) {
        AppointmentsEntity currentAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointmentRepository.delete(currentAppointment);

    }

    @Override
    public AppointmentDTO setCustomerToAppointment(AppointmentDTO appointmentDTO) {
        CustomerEntity customerEntity = findCustomerById(appointmentDTO.getCustomer());
        BeautyProceduresEntity beautyProceduresEntity = findBeautyProcedureById(appointmentDTO.getBeautyProcedure());
        AppointmentsEntity appointmentsEntity = findAppointmentById(appointmentDTO.getId());

        appointmentsEntity.setCustomer(customerEntity);
        appointmentsEntity.setBeautyProcedure(beautyProceduresEntity);
        appointmentsEntity.setAppointmentsOpen(false);

        AppointmentsEntity updatedAppointmentEntity = appointmentRepository.save(appointmentsEntity);

        sendAppointmentToQueue(updatedAppointmentEntity);
        return builderAppointmentsDTO(updatedAppointmentEntity);

    }

    private AppointmentsEntity findAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    private BeautyProceduresEntity findBeautyProcedureById(Long id) {
        return beautyProcedureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beauty procedure not found"));
    }

    private CustomerEntity findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private AppointmentDTO builderAppointmentsDTO(AppointmentsEntity appointmentsEntity) {
        return AppointmentDTO.builder()
                .id(appointmentsEntity.getId())
                .beautyProcedure(appointmentsEntity.getBeautyProcedure().getId())
                .dateTime(appointmentsEntity.getDateTime())
                .appointmentsOpen(appointmentsEntity.getAppointmentsOpen())
                .customer(appointmentsEntity.getCustomer().getId())
                .build();
    }

    private void sendAppointmentToQueue(AppointmentsEntity appointmentsEntity) {
        CustomerDTO customerDTO = appointmentsEntity.getCustomer() != null ? modelMapper.map(appointmentsEntity.getCustomer(), CustomerDTO.class) : null;
        BeautyProcedureDTO beautyProcedureDTO = appointmentsEntity.getBeautyProcedure() != null ? modelMapper.map(appointmentsEntity.getBeautyProcedure(), BeautyProcedureDTO.class) : null;
        FullAppointmentDTO fullAppointmentDTO = FullAppointmentDTO.builder()
                .id(appointmentsEntity.getId())
                .dateTime(appointmentsEntity.getDateTime())
                .appointmentsOpen(appointmentsEntity.getAppointmentsOpen())
                .customer(customerDTO)
                .beautyProcedure(beautyProcedureDTO)
                .build();

        brokerService.send("appointments", fullAppointmentDTO);
    }
}
