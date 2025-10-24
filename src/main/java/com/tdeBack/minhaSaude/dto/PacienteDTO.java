package com.tdeBack.minhaSaude.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PacienteDTO {
    private Long id;
    private String cpf;
    private String email;
    private String nome;
    private String telefone;
    private Date dataNascimento;
    private String estado;
    private String cidade;
    private String bairro;
    private Long responsavelId;
}
