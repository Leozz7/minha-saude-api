package com.tdeBack.minhaSaude.dto;

import com.tdeBack.minhaSaude.enums.TipoPagamento;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AtendimentoDTO {
    private Long usuarioId;
    private Long pacienteId;
    private String numeroCarteira;
    private TipoPagamento tipoPagamento;
    private Date dataAtendimento;
    private Double valorTotal;
    private List<Long> procedimentoIds;
}
