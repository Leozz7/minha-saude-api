package com.tdeBack.minhaSaude.dto.entrada;

import com.tdeBack.minhaSaude.model.Responsavel;
import lombok.*;

import java.util.Date;

@Data
public class PacienteDTO {
    private String cpf;
    private String email;
    private String nome;
    private String telefone;
    private Date dataNascimento;
    private String estado;
    private String cidade;
    private String bairro;
    private Responsavel responsavel;
}
