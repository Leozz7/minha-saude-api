package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.dto.ProcedimentoDTO;
import com.tdeBack.minhaSaude.model.Procedimento;
import com.tdeBack.minhaSaude.service.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedimentos")
public class ProcedimentoController {

    @Autowired
    private ProcedimentoService procedimentoService;

    @PostMapping("/criar")
    public ResponseEntity<Procedimento> criar(@RequestBody ProcedimentoDTO dto) {
        Procedimento p = new Procedimento();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setValorPlano(dto.getValorPlano());
        p.setValorParticular(dto.getValorParticular());
        return ResponseEntity.ok(procedimentoService.criar(p));
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<Procedimento> atualizar(@PathVariable Long id, @RequestBody ProcedimentoDTO dto) {
        Procedimento p = new Procedimento();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setValorPlano(dto.getValorPlano());
        p.setValorParticular(dto.getValorParticular());
        return ResponseEntity.ok(procedimentoService.atualizar(id, p));
    }

    @GetMapping("/listar")
    public Page<Procedimento> listar(Pageable pageable) {
        return procedimentoService.listar(pageable);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        procedimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
