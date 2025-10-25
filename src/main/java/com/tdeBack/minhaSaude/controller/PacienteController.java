package com.tdeBack.minhaSaude.controller;

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
    ResponseEntity<Paciente> criar(@RequestBody Paciente p) {
        var paciente = pacienteService.criarPaciente(p);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<Paciente> atualizar(@RequestBody Paciente p, @PathVariable Long id) {
        var paciente = pacienteService.atualizarPaciente(p, id);
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
