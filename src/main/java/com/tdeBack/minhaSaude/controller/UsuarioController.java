package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/criar")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario u) {

        if (usuarioService.existsByEmail(u.getEmail())) {
            return ResponseEntity.badRequest().build();
        } else {
            u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
            Usuario usuario = usuarioService.criarUsuario(u);
            return ResponseEntity.ok(usuario);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario u) {
        var UsernamePassword = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getSenha());
        var auth = this.authenticationManager.authenticate(UsernamePassword);

        return ResponseEntity.ok(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario u, @PathVariable Long id) {
        Usuario usuario = usuarioService.atualizarUsuario(id, u);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> lista = usuarioService.listar();
        return ResponseEntity.ok(lista);
    }
}
