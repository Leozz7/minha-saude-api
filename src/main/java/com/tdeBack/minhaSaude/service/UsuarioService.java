package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.enums.TipoUsuario;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuario.getTipo() == null) {
            usuario.setTipo(TipoUsuario.USER);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario uAtualizado) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));

        uAtualizado.setSenha(passwordEncoder.encode(uAtualizado.getSenha()));

        u.setNome(uAtualizado.getNome());
        u.setEmail(uAtualizado.getEmail());
        u.setSenha(uAtualizado.getSenha());
        return usuarioRepository.save(u);
    }

    public Usuario login(Usuario u) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return (Usuario) auth.getPrincipal();
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));

        usuarioRepository.delete(u);
    }

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
}
