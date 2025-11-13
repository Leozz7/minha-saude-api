package com.tdeBack.minhaSaude.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import com.tdeBack.minhaSaude.dto.AtendimentoDTO;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody AtendimentoDTO dto) {
        try {
            Atendimento atendimento = new Atendimento();
            atendimento.setNumeroCarteira(dto.getNumeroCarteira());
            atendimento.setTipoPagamento(dto.getTipoPagamento());
            atendimento.setProcedimentoIds(dto.getProcedimentoIds());
            atendimento.setDataAtendimento(dto.getDataAtendimento());

            Atendimento novo = atendimentoService.criar(atendimento, dto.getUsuarioId(), dto.getPacienteId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtendimentoDTO dto) {
        try {
            Atendimento atendimento = new Atendimento();
            atendimento.setNumeroCarteira(dto.getNumeroCarteira());
            atendimento.setTipoPagamento(dto.getTipoPagamento());
            atendimento.setProcedimentoIds(dto.getProcedimentoIds());
            atendimento.setDataAtendimento(dto.getDataAtendimento());

            Atendimento atualizado = atendimentoService.atualizar(id, atendimento);
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
