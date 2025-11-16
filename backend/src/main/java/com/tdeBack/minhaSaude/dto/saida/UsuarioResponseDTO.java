package com.tdeBack.minhaSaude.dto.saida;

import com.tdeBack.minhaSaude.enums.TipoUsuario;
import com.tdeBack.minhaSaude.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String email;
    private String nome;
    private TipoUsuario tipoUsuario;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.tipoUsuario = usuario.getTipo();
    }
}
