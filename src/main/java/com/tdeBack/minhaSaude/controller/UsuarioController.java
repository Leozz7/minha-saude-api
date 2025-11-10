package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.Config.Security.Token;
import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private Token tokenService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario u) {
        try {
            Usuario usuario = usuarioService.criarUsuario(u);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario u) {

        Usuario usuarioLogado = usuarioService.login(u);

        String token = tokenService.gerarToken(usuarioLogado);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", usuarioLogado.getEmail(),
                "nome", usuarioLogado.getNome()
        ));
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario u, @PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.atualizarUsuario(id, u);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public Page<Usuario> listar(Pageable pageable) {
        return usuarioService.listar(pageable);
    }
}
