package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findByPacienteId(Long PacienteId);
}
