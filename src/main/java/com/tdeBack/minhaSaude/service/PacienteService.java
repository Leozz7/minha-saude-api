package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import com.tdeBack.minhaSaude.repository.PacienteRepository;
import com.tdeBack.minhaSaude.repository.ResponsavelRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ResponsavelRepository responsavelRepository;

    @Transactional
    public Paciente criarPaciente(Paciente paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF ja cadastrado");
        }

        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new IllegalArgumentException("email ja cadastrado");
        }

        return pacienteRepository.save(paciente);
    }

    @Transactional
    public Paciente atualizarPaciente(Paciente paciente, Long id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        p.setCpf(paciente.getCpf());
        p.setBairro(paciente.getBairro());
        p.setCidade(paciente.getCidade());
        p.setNome(paciente.getNome());
        p.setEstado(paciente.getEstado());
        p.setTelefone(paciente.getTelefone());
        p.setDataNascimento(paciente.getDataNascimento());
        p.setResponsavel(paciente.getResponsavel());

        return pacienteRepository.save(p);
    }

    @Transactional
    public void deletarPaciente(Long id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        pacienteRepository.delete(p);
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

}
