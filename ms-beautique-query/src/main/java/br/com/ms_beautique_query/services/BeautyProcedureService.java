package br.com.ms_beautique_query.services;

import br.com.ms_beautique_query.dtos.beautyprocedures.BeautyProcedureDTO;

import java.util.List;

public interface BeautyProcedureService {

    List<BeautyProcedureDTO> listAllBeautyProcedures();
    List<BeautyProcedureDTO> listByNameIgnoreCase(String name);
    List<BeautyProcedureDTO> listByDescriptionIgnoreCase(String email);
}
