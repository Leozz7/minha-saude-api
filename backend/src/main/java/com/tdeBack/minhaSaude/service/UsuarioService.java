package com.tdeBack.minhaSaude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdeBack.minhaSaude.dto.entrada.UsuarioDTO;
import com.tdeBack.minhaSaude.dto.saida.UsuarioResponseDTO;
import com.tdeBack.minhaSaude.enums.TipoUsuario;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNome(dto.getNome());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        if (dto.getTipoUsuario() == null) {
            usuario.setTipo(TipoUsuario.USER);
        } else {
            usuario.setTipo(dto.getTipoUsuario());
        }

        Usuario u = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(u);
    }


    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, Usuario uAtualizado) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));

        uAtualizado.setSenha(passwordEncoder.encode(uAtualizado.getSenha()));

        u.setNome(uAtualizado.getNome());
        u.setEmail(uAtualizado.getEmail());
        u.setSenha(uAtualizado.getSenha());

        Usuario atualizado = usuarioRepository.save(u);
        return new UsuarioResponseDTO(atualizado);
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

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));
        return new UsuarioResponseDTO(u);
    }
}
