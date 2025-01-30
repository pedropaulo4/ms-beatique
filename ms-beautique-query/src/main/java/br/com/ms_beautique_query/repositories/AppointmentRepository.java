package br.com.ms_beautique_query.repositories;

import br.com.ms_beautique_query.dtos.appointments.FullAppointmentDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AppointmentRepository extends MongoRepository <FullAppointmentDTO, Long> {
    @Query("{'customerId': ?0 }")
    List<FullAppointmentDTO> listAppointmentByCustomerId(Long id);

    @Query("{'beautyProcedureId': ?0 }")
    List<FullAppointmentDTO> listAppointmentByBeautyProcedureId(Long id);
}
