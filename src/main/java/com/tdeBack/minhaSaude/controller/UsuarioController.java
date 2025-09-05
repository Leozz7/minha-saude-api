package com.tdeBack.minhaSaude.controller;

import com.tdeBack.minhaSaude.model.Usuario;
import com.tdeBack.minhaSaude.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario u) {
        Usuario usuario = usuarioService.criarUsuario(u);
        return ResponseEntity.ok(usuario);
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

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscaEmail(@PathVariable String email) {
        return usuarioService.buscarEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
