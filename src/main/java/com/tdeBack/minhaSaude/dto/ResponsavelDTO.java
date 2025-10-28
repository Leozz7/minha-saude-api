package com.tdeBack.minhaSaude.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponsavelDTO {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private Date dataNascimento;
}
