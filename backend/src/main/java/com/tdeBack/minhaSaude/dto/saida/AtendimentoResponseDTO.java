package com.tdeBack.minhaSaude.dto.saida;

import com.tdeBack.minhaSaude.dto.entrada.PacienteDTO;
import com.tdeBack.minhaSaude.model.Atendimento;
import com.tdeBack.minhaSaude.model.Paciente;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AtendimentoResponseDTO {

    private Long id;
    private Long usuarioId;
    private PacienteResponseDTO paciente;
    private String numeroCarteira;
    private Date dataAtendimento;
    private Double valorTotal;

    private List<ProcedimentoResponseDTO> procedimentos;

    public AtendimentoResponseDTO(Atendimento atendimento) {
        this.id = atendimento.getId();
        this.usuarioId = atendimento.getUsuario().getId();
        this.paciente = new PacienteResponseDTO(atendimento.getPaciente());
        this.numeroCarteira = atendimento.getNumeroCarteira();
        this.dataAtendimento = atendimento.getDataAtendimento();
        this.valorTotal = atendimento.getValorTotal();

        this.procedimentos = atendimento.getProcedimentos()
                .stream()
                .map(ProcedimentoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
