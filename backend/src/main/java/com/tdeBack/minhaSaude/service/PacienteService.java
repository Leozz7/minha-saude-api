package com.tdeBack.minhaSaude.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.tdeBack.minhaSaude.dto.saida.ProcedimentoResponseDTO;
import com.tdeBack.minhaSaude.model.Procedimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdeBack.minhaSaude.dto.entrada.PacienteDTO;
import com.tdeBack.minhaSaude.dto.saida.PacienteResponseDTO;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import com.tdeBack.minhaSaude.repository.AtendimentoRepository;
import com.tdeBack.minhaSaude.repository.PacienteRepository;
import com.tdeBack.minhaSaude.repository.ResponsavelRepository;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ResponsavelRepository responsavelRepository;

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Transactional
    public PacienteResponseDTO criar(PacienteDTO dto) {

        Paciente paciente = new Paciente();

        if (pacienteRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF de paciente ja cadastrado");
        }

        if (pacienteRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email de paciente ja cadastrado");
        }

        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setNome(dto.getNome());
        paciente.setCidade(dto.getCidade());
        paciente.setBairro(dto.getBairro());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setEstado(dto.getEstado());
        paciente.setTelefone(dto.getTelefone());
        paciente.setResponsavel(dto.getResponsavel());

        validarPacienteDeMenor(paciente);

        Paciente salvo = pacienteRepository.save(paciente);

        return new PacienteResponseDTO(salvo);
    }

    @Transactional
    public PacienteResponseDTO atualizar(PacienteDTO paciente, Long id) {
        Paciente p = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF de paciente ja cadastrado");
        }

        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new IllegalArgumentException("Email de paciente ja cadastrado");
        }

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

        Paciente salvo = pacienteRepository.save(p);

        return new PacienteResponseDTO(salvo);
    }


    @Transactional
    public void deletar(Long id) {
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

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Paciente nao encontrado"));
        return new PacienteResponseDTO(p);
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

            if (responsavelRepository.existsByEmail(paciente.getResponsavel().getEmail())) {
                throw new IllegalArgumentException("email de responsavel ja cadastrado");
            }
        } else {
            paciente.setResponsavel(null);
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
