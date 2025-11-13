package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.dto.PacienteDTO;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import com.tdeBack.minhaSaude.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping("/criar")
    ResponseEntity<?> criar(@RequestBody PacienteDTO dto) {
        try {
            Paciente paciente = pacienteService.criarPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<?> atualizar(@RequestBody PacienteDTO dto, @PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.atualizarPaciente(dto, id);
            return ResponseEntity.ok(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/listar")
    public Page<Paciente> listar(Pageable pageable) {
        return pacienteService.listar(pageable);
    }
    @GetMapping("/buscar/{nome}")
    public Page<Paciente> buscarPorNome(@PathVariable String nome, Pageable pageable) {
        return pacienteService.buscarPorNome(nome, pageable);
    }
}
