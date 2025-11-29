package com.tdeBack.minhaSaude.dto.entrada;

import com.tdeBack.minhaSaude.enums.TipoPagamento;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AtendimentoDTO {

    private Long pacienteId;
    private String numeroCarteira;
    private Date dataAtendimento;
    private TipoPagamento tipoPagamento;
    private List<Long> procedimentoIds;
}