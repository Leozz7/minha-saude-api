package com.tdeBack.minhaSaude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tdeBack.minhaSaude.dto.AtendimentoDTO;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.service.AtendimentoService;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody AtendimentoDTO dto) {
        try {
            Atendimento novo = atendimentoService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtendimentoDTO dto) {
        try {
            Atendimento atualizado = atendimentoService.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Atendimento>> listar() {
        return ResponseEntity.ok(atendimentoService.listar());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            atendimentoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
