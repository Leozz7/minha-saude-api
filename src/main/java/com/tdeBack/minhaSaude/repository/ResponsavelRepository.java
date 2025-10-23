package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    Responsavel findByCpf(String cpf);
    boolean existsByCpf(String cpf);

    Responsavel findByEmail(String email);
    boolean existsByEmail(String email);
}
