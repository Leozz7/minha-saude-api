package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.dto.PacienteDTO;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import com.tdeBack.minhaSaude.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @PostMapping("/criar")
    ResponseEntity<Paciente> criar(@RequestBody PacienteDTO dto) {
        Paciente paciente = pacienteService.criarPaciente(dto);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<Paciente> atualizar(@RequestBody PacienteDTO dto, @PathVariable Long id) {
        Paciente paciente = pacienteService.atualizarPaciente(dto, id);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/deletar/{id}")
    ResponseEntity<String> deletar(@PathVariable Long id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.ok("Paciente deletado");
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
