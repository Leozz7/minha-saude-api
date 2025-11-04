package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.model.Procedimento;
import com.tdeBack.minhaSaude.repository.AtendimentoRepository;
import com.tdeBack.minhaSaude.repository.ProcedimentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedimentoService {
    @Autowired
    ProcedimentoRepository procedimentoRepository;

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Transactional
    public Procedimento criar(Procedimento p) {
        if (procedimentoRepository.existsByNome(p.getNome())) {
            throw new IllegalArgumentException("Nome de procedimento ja cadastrado");
        }
        return procedimentoRepository.save(p);
    }

    @Transactional
    public void deletar(Long id) {
        Procedimento p = procedimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Procedimento nao encontrado"));
        procedimentoRepository.delete(p);
    }

    @Transactional
    public Procedimento atualizar(Long id, Procedimento procedimento) {

        Procedimento p = procedimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        Procedimento existente = procedimentoRepository.findByNome(procedimento.getNome());
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalArgumentException("Nome de procedimento já cadastrado");
        }
        p.setNome(procedimento.getNome());
        p.setDescricao(procedimento.getDescricao());
        p.setValorPlano(procedimento.getValorPlano());
        p.setValorParticular(procedimento.getValorParticular());

        return procedimentoRepository.save(p);
    }

    public Page<Procedimento> listar(Pageable pageable) {
        return procedimentoRepository.findAll(pageable);
    }
}
