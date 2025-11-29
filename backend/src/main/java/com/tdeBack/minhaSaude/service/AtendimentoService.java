package com.tdeBack.minhaSaude.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdeBack.minhaSaude.dto.entrada.AtendimentoDTO;
import com.tdeBack.minhaSaude.dto.saida.AtendimentoResponseDTO;
import com.tdeBack.minhaSaude.enums.TipoPagamento;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.model.Procedimento;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.AtendimentoRepository;
import com.tdeBack.minhaSaude.repository.PacienteRepository;
import com.tdeBack.minhaSaude.repository.ProcedimentoRepository;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;

@Service
public class AtendimentoService {

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Autowired
    ProcedimentoRepository procedimentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    public AtendimentoResponseDTO criar(AtendimentoDTO dto) {

        Atendimento atendimento = converterDTO(dto);

        validarAtendimento(atendimento);

        List<Procedimento> procedimentos =
                procedimentoRepository.findAllById(atendimento.getProcedimentoIds());

        if (procedimentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum procedimento encontrado.");
        }

        atendimento.setProcedimentos(procedimentos);
        atendimento.setValorTotal(calculoValorProcedimentos(procedimentos, atendimento));

        verificarCarteira(atendimento);

        atendimento = atendimentoRepository.save(atendimento);

        return new AtendimentoResponseDTO(atendimento);
    }


    @Transactional
    public void deletar(Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado."));
        atendimentoRepository.delete(atendimento);
    }

    @Transactional
    public AtendimentoResponseDTO atualizar(Long id, AtendimentoDTO dto) {

        Atendimento atendimento = converterDTO(dto);

        Atendimento a = atendimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado."));

        validarAtendimento(atendimento);


        a.setDataAtendimento(atendimento.getDataAtendimento());
        a.setTipoPagamento(atendimento.getTipoPagamento());
        a.setPaciente(atendimento.getPaciente());
        a.setUsuario(atendimento.getUsuario());
        a.setNumeroCarteira(atendimento.getNumeroCarteira());
        a.setUsuario(atendimento.getUsuario());
        a.setPaciente(atendimento.getPaciente());

        verificarCarteira(a);

        List<Procedimento> procedimentos = procedimentoRepository.findAllById(atendimento.getProcedimentoIds());
        if (procedimentos.isEmpty()) {
            throw new IllegalArgumentException("O atendimento deve conter pelo menos um procedimento válido.");
        }

        a.setProcedimentos(procedimentos);

        a.setValorTotal(calculoValorProcedimentos(procedimentos, atendimento));

        Atendimento atualizado = atendimentoRepository.save(a);

        return new AtendimentoResponseDTO(atualizado);
    }

    public Page<Atendimento> listarPorPeriodo(Date inicio, Date fim, Pageable pageable) {
        return atendimentoRepository.findByDataAtendimentoBetween(inicio, fim, pageable);
    }

    public Page<Atendimento> listar(Pageable pageable) {
        return atendimentoRepository.findAll(pageable);
    }

    public AtendimentoResponseDTO buscarPorId(Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento nao encontrado"));
        return new AtendimentoResponseDTO(atendimento);
    }

    private void verificarCarteira(Atendimento atendimento) {
        if (atendimento.getTipoPagamento() == TipoPagamento.PLANO &&
                (atendimento.getNumeroCarteira() == null || atendimento.getNumeroCarteira().isEmpty())) {
            throw new IllegalArgumentException("Número da carteira do plano de saúde é obrigatório para atendimentos de plano.");
        }
    }

    private void validarAtendimento(Atendimento atendimento) {
        if (atendimento.getProcedimentoIds() == null || atendimento.getProcedimentoIds().isEmpty()) {
            throw new IllegalArgumentException("O atendimento deve conter pelo menos um procedimento.");
        }
        if (atendimento.getTipoPagamento() == null) {
            throw new IllegalArgumentException("Tipo de pagamento é obrigatório.");
        }
    }

    public double calculoValorProcedimentos(List<Procedimento> procedimentos, Atendimento atendimento) {
        double valorTotal = 0.0;
        for (Procedimento p : procedimentos) {
            if (atendimento.getTipoPagamento() == TipoPagamento.PARTICULAR) {
                valorTotal += p.getValorParticular();
            } else {
                valorTotal += p.getValorPlano();
            }
        }
        return valorTotal;
    }

    public Atendimento converterDTO(AtendimentoDTO dto) {
        Usuario usuario = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Atendimento atendimento = new Atendimento();

        atendimento.setNumeroCarteira(dto.getNumeroCarteira());
        atendimento.setTipoPagamento(dto.getTipoPagamento());
        atendimento.setProcedimentoIds(dto.getProcedimentoIds());
        atendimento.setDataAtendimento(dto.getDataAtendimento());
        atendimento.setUsuario(usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado")));

        atendimento.setPaciente(
                pacienteRepository.findById(dto.getPacienteId())
                        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"))
        );

        return atendimento;
    }

}