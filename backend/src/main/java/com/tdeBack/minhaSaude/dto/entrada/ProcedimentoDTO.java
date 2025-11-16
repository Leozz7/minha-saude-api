package com.tdeBack.minhaSaude.dto.entrada;

import com.tdeBack.minhaSaude.model.Procedimento;
import lombok.Data;

@Data
public class ProcedimentoDTO {
    private String nome;
    private String descricao;
    private Float valorPlano;
    private Float valorParticular;
}
