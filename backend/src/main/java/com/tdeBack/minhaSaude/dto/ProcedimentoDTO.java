package com.tdeBack.minhaSaude.dto;

import lombok.Data;

@Data
public class ProcedimentoDTO {
    private String nome;
    private String descricao;
    private Float valorPlano;
    private Float valorParticular;
}
