package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface PacienteRepository extends JpaRepository<Paciente ,Long> {
    Paciente findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    Paciente findByEmail(String email);
    boolean existsByEmail(String email);
}
