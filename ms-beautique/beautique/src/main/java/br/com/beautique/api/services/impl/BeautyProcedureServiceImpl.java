package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.BeautyProcedureDTO;
import br.com.beautique.api.entities.BeautyProceduresEntity;
import br.com.beautique.api.repositories.BeautyProcedureRepository;
import br.com.beautique.api.services.BeautyProcedureService;
import br.com.beautique.api.services.BrokerService;
import br.com.beautique.api.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeautyProcedureServiceImpl implements BeautyProcedureService {

    @Autowired
    private BeautyProcedureRepository beautyProcedureRepository;

    private final ConvertUtil<BeautyProceduresEntity, BeautyProcedureDTO> convertUtil = new ConvertUtil<>(BeautyProceduresEntity.class, BeautyProcedureDTO.class);

    @Autowired
    private BrokerService brokerService;

    @Override
    public BeautyProcedureDTO create(BeautyProcedureDTO beautyProcedureDTO) {
        BeautyProceduresEntity beautyProceduresEntity = convertUtil.convertToSource(beautyProcedureDTO);
        BeautyProceduresEntity newBeautyProcedureEntity = beautyProcedureRepository.save(beautyProceduresEntity);

        sendBeautyProcedureQueue(newBeautyProcedureEntity);
        return convertUtil.convertToTarget(newBeautyProcedureEntity);
    }

    @Override
    public void delete(Long id) {
        Optional<BeautyProceduresEntity> beautyProceduresEntity = beautyProcedureRepository.findById(id);
        if (beautyProceduresEntity.isEmpty()) {
            throw  new RuntimeException("Beauty Procedure Not Found");
        }

        beautyProcedureRepository.deleteById(id);
    }

    @Override
    public BeautyProcedureDTO update(BeautyProcedureDTO beautyProcedureDTO) {
        Optional<BeautyProceduresEntity> beautyProceduresEntity = beautyProcedureRepository.findById(beautyProcedureDTO.getId());
        if (beautyProceduresEntity.isEmpty()) {
            throw  new RuntimeException("Beauty Procedure Not Found");
        }

        BeautyProceduresEntity beautyProcedureUpdate = convertUtil.convertToSource(beautyProcedureDTO);
        beautyProcedureUpdate.setAppoitments(beautyProceduresEntity.get().getAppoitments());
        beautyProcedureUpdate.setCreatedAt(beautyProceduresEntity.get().getCreatedAt());

        sendBeautyProcedureQueue(beautyProcedureUpdate);

        return convertUtil.convertToTarget(beautyProcedureRepository.save(beautyProcedureUpdate));
    }

    private void sendBeautyProcedureQueue(BeautyProceduresEntity beautyProceduresEntity) {
        BeautyProcedureDTO procedureDTO = BeautyProcedureDTO.builder()
                .id(beautyProceduresEntity.getId())
                .name(beautyProceduresEntity.getName())
                .description(beautyProceduresEntity.getDescription())
                .price(beautyProceduresEntity.getPrice())
                .build();

        brokerService.send("beautyProcedures", beautyProceduresEntity);

    }
}
