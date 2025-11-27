package com.tdeBack.minhaSaude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tdeBack.minhaSaude.dto.entrada.AtendimentoDTO;
import com.tdeBack.minhaSaude.dto.saida.AtendimentoResponseDTO;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.service.AtendimentoService;

import java.util.Date;


@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody AtendimentoDTO dto) {
        AtendimentoResponseDTO atendimento = atendimentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimento);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtendimentoDTO dto) {
        AtendimentoResponseDTO atualizado = atendimentoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok(
                atendimentoService.listar(pageable)
                        .map(AtendimentoResponseDTO::new)
        );
    }

    @GetMapping("/listar-periodo")
    public ResponseEntity<?> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fim,
            Pageable pageable) {

        return ResponseEntity.ok(
                atendimentoService.listarPorPeriodo(inicio, fim, pageable)
                        .map(AtendimentoResponseDTO::new)
        );
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        atendimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

        AtendimentoResponseDTO atendimento = atendimentoService.buscarPorId(id);
        return ResponseEntity.ok(atendimento);
    }
    
}
