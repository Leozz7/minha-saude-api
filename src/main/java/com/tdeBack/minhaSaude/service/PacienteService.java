package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.dto.PacienteDTO;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import com.tdeBack.minhaSaude.repository.AtendimentoRepository;
import com.tdeBack.minhaSaude.repository.PacienteRepository;
import com.tdeBack.minhaSaude.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ResponsavelRepository responsavelRepository;

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Transactional
    public Paciente criarPaciente(PacienteDTO dto) {

        Paciente paciente = new Paciente();
        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setNome(dto.getNome());
        paciente.setCidade(dto.getCidade());
        paciente.setBairro(dto.getBairro());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setEstado(dto.getEstado());
        paciente.setTelefone(dto.getTelefone());
        paciente.setResponsavel(dto.getResponsavel());

        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF de paciente ja cadastrado");
        }

        if (paciente.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF de paciente invalido");
        }

        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new IllegalArgumentException("email de paciente ja cadastrado");
        }

        if (responsavelRepository.existsByCpf(paciente.getResponsavel().getCpf())) {
            throw new IllegalArgumentException("CPF de responsavel ja cadastrado");
        }

        if (paciente.getResponsavel().getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF de responsavel invalido");
        }

        if (responsavelRepository.existsByEmail(paciente.getResponsavel().getEmail())) {
            throw new IllegalArgumentException("email de responsavel ja cadastrado");
        }

        validarPacienteDeMenor(paciente);

        return pacienteRepository.save(paciente);
    }

    @Transactional
    public Paciente atualizarPaciente(PacienteDTO paciente, Long id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        p.setCpf(paciente.getCpf());
        p.setBairro(paciente.getBairro());
        p.setCidade(paciente.getCidade());
        p.setNome(paciente.getNome());
        p.setEstado(paciente.getEstado());
        p.setTelefone(paciente.getTelefone());
        p.setDataNascimento(paciente.getDataNascimento());

        Responsavel responsavel = responsavelRepository.findById(paciente.getResponsavel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Responsavel não encontrado"));

        p.setResponsavel(responsavel);
        validarPacienteDeMenor(p);

        responsavelRepository.save(p.getResponsavel());
        return pacienteRepository.save(p);
    }

    @Transactional
    public void deletarPaciente(Long id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        if (!atendimentoRepository.findByPacienteId(id).isEmpty()) {
            throw new IllegalArgumentException("existe um atendimento registrado com esse paciente");
        }

        pacienteRepository.delete(p);
    }

    public Page<Paciente> listar(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    public Page<Paciente> buscarPorNome(String nome, Pageable pageable) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public void validarPacienteDeMenor(Paciente paciente) {
        var idade = calcularIdade(paciente.getDataNascimento());

        if (idade < 0) {
            throw new IllegalArgumentException("a idade do paciente é negativa");
        }

        if (idade < 18) {
            if (paciente.getResponsavel() == null) {
                throw new IllegalArgumentException("Pacientes menores de 18 anos devem ter um responsavel cadastrado");
            }

            int idadeResp = calcularIdade(paciente.getResponsavel().getDataNascimento());

            if (idadeResp < 18) {
                throw new IllegalArgumentException("O responsavel não pode ser menor de idade");
            }
        }
    }

    public LocalDate converterData(Date data) {
        return data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public int calcularIdade(Date data) {
        return Period.between(converterData(data), LocalDate.now()).getYears();
    }
}
