package br.com.ms_beautique_query.services.impl;

import br.com.ms_beautique_query.dtos.beautyprocedures.BeautyProcedureDTO;
import br.com.ms_beautique_query.repositories.BeautyProcedureRepository;
import br.com.ms_beautique_query.services.BeautyProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeautyProcedureServiceImpl implements BeautyProcedureService {

    @Autowired
    private BeautyProcedureRepository beautyProcedureRepository;

    @Override
    public List<BeautyProcedureDTO> listAllBeautyProcedures() {
        try{
            return beautyProcedureRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException("Error Listining Beauty Procedure List");
        }

    }

    @Override
    public List<BeautyProcedureDTO> listByNameIgnoreCase(String name) {
        try{
            return beautyProcedureRepository.findByNameIgnoreCase(name);
        }catch(Exception e){
            throw new RuntimeException("Error listing beauty procedure by name");
        }
    }

    @Override
    public List<BeautyProcedureDTO> listByDescriptionIgnoreCase(String descricao) {
        try{
            return beautyProcedureRepository.findByDescriptionIgnoreCase(descricao);
        }catch(Exception e){
            throw new RuntimeException("Error listing beauty procedure by descricao");
        }
    }
}
