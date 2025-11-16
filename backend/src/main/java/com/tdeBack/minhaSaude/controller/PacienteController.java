package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.dto.entrada.AtendimentoDTO;
import com.tdeBack.minhaSaude.dto.saida.AtendimentoResponseDTO;
import com.tdeBack.minhaSaude.dto.saida.PacienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdeBack.minhaSaude.dto.entrada.PacienteDTO;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.service.PacienteService;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody PacienteDTO dto) {
        try {
            PacienteResponseDTO paciente = pacienteService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<?> atualizar(@RequestBody PacienteDTO dto, @PathVariable Long id) {
        try {
            PacienteResponseDTO paciente = pacienteService.atualizar(dto, id);
            return ResponseEntity.ok(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            pacienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok(
                pacienteService.listar(pageable)
                        .map(PacienteResponseDTO::new)
        );
    }

    @GetMapping("/buscar/{nome}")
    public Page<Paciente> buscarPorNome(@PathVariable String nome, Pageable pageable) {
        return pacienteService.buscarPorNome(nome, pageable);
    }
}
