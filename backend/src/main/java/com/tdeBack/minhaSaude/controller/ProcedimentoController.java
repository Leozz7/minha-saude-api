package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.dto.entrada.ProcedimentoDTO;
import com.tdeBack.minhaSaude.dto.saida.AtendimentoResponseDTO;
import com.tdeBack.minhaSaude.dto.saida.PacienteResponseDTO;
import com.tdeBack.minhaSaude.dto.saida.ProcedimentoResponseDTO;
import com.tdeBack.minhaSaude.model.Procedimento;
import com.tdeBack.minhaSaude.service.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/procedimentos")
public class ProcedimentoController {

    @Autowired
    private ProcedimentoService procedimentoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody ProcedimentoDTO dto) {
        try {
            Procedimento p = procedimentoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProcedimentoResponseDTO(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProcedimentoDTO dto) {
        try {
            Procedimento p = procedimentoService.atualizar(id, dto);
            return ResponseEntity.ok(new ProcedimentoResponseDTO(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok(
                procedimentoService.listar(pageable)
                        .map(ProcedimentoResponseDTO::new)
        );
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            procedimentoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
