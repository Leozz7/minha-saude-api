package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    Procedimento findByNome(String nome);
    boolean existsByNome(String nome);
}
