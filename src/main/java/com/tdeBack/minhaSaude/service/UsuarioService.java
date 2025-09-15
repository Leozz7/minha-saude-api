package com.tdeBack.minhaSaude.service;

import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario uAtualizado) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        u.setNome(uAtualizado.getNome());
        u.setEmail(uAtualizado.getEmail());
        return usuarioRepository.save(u);
    }

    public void deletarUsuario(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        usuarioRepository.delete(u);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}
