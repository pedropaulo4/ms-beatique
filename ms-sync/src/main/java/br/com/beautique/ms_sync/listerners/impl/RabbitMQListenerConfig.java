package br.com.beautique.ms_sync.listerners.impl;

import br.com.beautique.ms_sync.dtos.appointments.FullAppointmentDTO;
import br.com.beautique.ms_sync.dtos.beautyprocedures.BeautyProcedureDTO;
import br.com.beautique.ms_sync.dtos.customers.CustomerDTO;
import br.com.beautique.ms_sync.listerners.ListenerConfig;
import br.com.beautique.ms_sync.services.SyncService;
import br.com.beautique.ms_sync.utils.SyncLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQListenerConfig implements ListenerConfig {

    private final ObjectMapper objectMapper;
    private final SyncService  syncService;

    public RabbitMQListenerConfig(ObjectMapper objectMapper, SyncService syncService) {
        this.objectMapper = objectMapper;
        this.syncService = syncService;
    }

    @RabbitListener(queues = "customerQueue")
    @Override
    public void listenToCustomerQueue(String message) {

        try{
            CustomerDTO customer = objectMapper.readValue(message, CustomerDTO.class);
            syncService.syncCustomer(customer);
            SyncLogger.info("Message received from queue customerQueue: " + customer.toString());
        }catch (Exception e) {
            SyncLogger.error("Error listen customer queue: " + e.getMessage());
        }

    }

    @RabbitListener(queues = "appointmentQueue")
    @Override
    public void listenToBeautyProcedureQueue(String message) {

        try{
            FullAppointmentDTO fullAppointment = objectMapper.readValue(message, FullAppointmentDTO.class);
            syncService.syncAppointment(fullAppointment);
            SyncLogger.info("Message received from queue appointmentQueue: " + fullAppointment.toString());
        }catch (Exception e) {
            SyncLogger.error("Error listen appointment queue: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "beautyProcedureQueue")
    @Override
    public void listenToAppointmentQueue(String message) {
        try{
            BeautyProcedureDTO beautyProcedure = objectMapper.readValue(message, BeautyProcedureDTO.class);
            syncService.syncBeautyProcedure(beautyProcedure);
            SyncLogger.info("Message received from queue beautyProcedureQueue: " + beautyProcedure.toString());
        }catch (Exception e) {
            SyncLogger.error("Error listen beautyProcedure queue: " + e.getMessage());
        }
    }
}
