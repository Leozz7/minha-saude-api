package com.tdeBack.minhaSaude.dto.saida;

import com.tdeBack.minhaSaude.model.Procedimento;
import lombok.Data;

@Data
public class ProcedimentoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Float valorPlano;
    private Float valorParticular;

    public ProcedimentoResponseDTO(Procedimento p) {
        this.id = p.getId();
        this.nome = p.getNome();
        this.descricao = p.getDescricao();
        this.valorPlano = p.getValorPlano();
        this.valorParticular = p.getValorParticular();
    }
}
