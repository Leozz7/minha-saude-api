package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.enums.TipoPagamento;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Procedimento;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.AtendimentoRepository;
import com.tdeBack.minhaSaude.repository.PacienteRepository;
import com.tdeBack.minhaSaude.repository.ProcedimentoRepository;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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
    public Atendimento criar(Atendimento atendimento, Long usuario_id, Long paciente_id) {

        validarAtendimento(atendimento);

        List<Procedimento> procedimentos = procedimentoRepository.findAllById(atendimento.getProcedimentoIds());

        if (procedimentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum procedimento encontrado para os ID informado.");
        }

        LocalDate dataAtendimento = atendimento.getDataAtendimento()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (dataAtendimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de atendimento não pode ser no futuro.");
        }
        atendimento.setUsuario(usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado")));

        atendimento.setPaciente(pacienteRepository.findById(paciente_id)
                .orElseThrow(() -> new IllegalArgumentException("paciente nao encontrado")));


        atendimento.setValorTotal(calculoValorProcedimentos(procedimentos, atendimento));
        atendimento.setProcedimentos(procedimentos);

        verificarCarteira(atendimento);

        return atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void deletar(Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado."));
        atendimentoRepository.delete(atendimento);
    }

    @Transactional
    public Atendimento atualizar(Long id, Atendimento atendimento) {
        Atendimento a = atendimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado."));

        validarAtendimento(atendimento);


        a.setDataAtendimento(atendimento.getDataAtendimento());
        a.setTipoPagamento(atendimento.getTipoPagamento());
        a.setPaciente(atendimento.getPaciente());
        a.setUsuario(atendimento.getUsuario());
        a.setNumeroCarteira(atendimento.getNumeroCarteira());

        verificarCarteira(a);

        List<Procedimento> procedimentos = procedimentoRepository.findAllById(atendimento.getProcedimentoIds());
        if (procedimentos.isEmpty()) {
            throw new IllegalArgumentException("O atendimento deve conter pelo menos um procedimento válido.");
        }

        a.setProcedimentos(procedimentos);

        a.setValorTotal(calculoValorProcedimentos(procedimentos, atendimento));

        return atendimentoRepository.save(a);
    }

    public List<Atendimento> listar() {
        return atendimentoRepository.findAll();
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
}
