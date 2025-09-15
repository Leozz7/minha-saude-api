package com.tdeBack.minhaSaude.repository;

import com.tdeBack.minhaSaude.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {
    UserDetails findByEmail(String email);
    boolean existsByEmail(String email);
}
