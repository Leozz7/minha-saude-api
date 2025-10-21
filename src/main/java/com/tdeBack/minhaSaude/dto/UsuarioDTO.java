package com.tdeBack.minhaSaude.dto;

import com.tdeBack.minhaSaude.enums.TipoUsuario;
import com.tdeBack.minhaSaude.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioDTO implements Serializable {

    private Long id;
    private String email;
    private String nome;
    private TipoUsuario tipo;

    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.tipo = usuario.getTipo();
    }

    public UsuarioDTO(String nome, @Email @NotBlank String email) {
    }
}

