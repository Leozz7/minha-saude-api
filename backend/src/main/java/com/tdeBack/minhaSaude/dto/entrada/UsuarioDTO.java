package com.tdeBack.minhaSaude.dto.entrada;

import com.tdeBack.minhaSaude.enums.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private String nome;
    private TipoUsuario tipoUsuario;
    private String senha;
}
