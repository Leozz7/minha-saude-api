package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public void criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario uAtualizado) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        usuarioRepository.delete(u);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
