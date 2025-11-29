package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findByPacienteId(Long PacienteId);
    Page<Atendimento> findByDataAtendimentoBetween(Date inicio, Date fim, Pageable pageable);
}