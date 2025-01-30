package br.com.beautique.api.controllers;

import br.com.beautique.api.dtos.BeautyProcedureDTO;
import br.com.beautique.api.services.BeautyProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("beauty-procedures")
public class BeautyProcedureController {

    @Autowired
    private BeautyProcedureService beautyProcedureService;

    @PostMapping
    public ResponseEntity<BeautyProcedureDTO> create (@RequestBody final BeautyProcedureDTO beautyProcedureDTO) {
        return ResponseEntity.ok(beautyProcedureService.create(beautyProcedureDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable final Long id) {
        beautyProcedureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<BeautyProcedureDTO> update (@RequestBody final BeautyProcedureDTO beautyProcedureDTO) {
        return ResponseEntity.ok(beautyProcedureService.update(beautyProcedureDTO));

    }
}
