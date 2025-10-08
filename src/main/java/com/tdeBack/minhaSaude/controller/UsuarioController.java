package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.Config.Security.Token;
import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario u) {

        if (usuarioService.existsByEmail(u.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email já cadastrado");
        } else {
            u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
            usuarioService.criarUsuario(u);
            return ResponseEntity.ok().body("Usuario cadastrado");
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
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario u, @PathVariable Long id) {
        Usuario usuario = usuarioService.atualizarUsuario(id, u);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuario deletado");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        var lista = usuarioService.listar();
        return ResponseEntity.ok(lista);
    }
}
