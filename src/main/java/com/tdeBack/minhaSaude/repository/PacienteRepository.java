package com.tdeBack.minhaSaude.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tdeBack.minhaSaude.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente ,Long> {
    Paciente findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    Paciente findByEmail(String email);
    boolean existsByEmail(String email);

    Page<Paciente> findByNomeContainingIgnoreCase(String name, Pageable pageable);
}
