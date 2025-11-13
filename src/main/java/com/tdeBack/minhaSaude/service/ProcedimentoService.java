package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.dto.ProcedimentoDTO;
import com.tdeBack.minhaSaude.model.Paciente;
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


    @Transactional
    public Procedimento criar(ProcedimentoDTO dto) {
        Procedimento p = new Procedimento();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setValorPlano(dto.getValorPlano());
        p.setValorParticular(dto.getValorParticular());
        if (procedimentoRepository.existsByNome(p.getNome())) {
            throw new IllegalArgumentException("Nome de procedimento ja cadastrado");
        }
        if (p.getValorPlano() < 0) {
            throw new IllegalArgumentException("o valor do plano esta negativo");
        }
        if (p.getValorParticular() < 0) {
            throw new IllegalArgumentException("o valor particular está negativo");
        }
        return procedimentoRepository.save(p);
    }

    @Transactional
    public void deletar(Long id) {
        Procedimento p = procedimentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Procedimento nao encontrado"));
        procedimentoRepository.delete(p);
    }

    @Transactional
    public Procedimento atualizar(Long id, ProcedimentoDTO dto) {

        Procedimento p = procedimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento não encontrado"));

        Procedimento existente = procedimentoRepository.findByNome(dto.getNome());
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalArgumentException("Nome de procedimento já cadastrado");
        }
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setValorPlano(dto.getValorPlano());
        p.setValorParticular(dto.getValorParticular());

        return procedimentoRepository.save(p);
    }

    public Page<Procedimento> listar(Pageable pageable) {
        return procedimentoRepository.findAll(pageable);
    }
}
