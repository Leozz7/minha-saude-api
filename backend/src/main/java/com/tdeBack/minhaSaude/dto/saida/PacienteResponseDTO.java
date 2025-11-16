package com.tdeBack.minhaSaude.dto.saida;

import com.tdeBack.minhaSaude.model.Paciente;
import com.tdeBack.minhaSaude.model.Responsavel;
import lombok.*;

import java.util.Date;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String cpf;
    private String email;
    private String nome;
    private String telefone;
    private Date dataNascimento;
    private String estado;
    private String cidade;
    private String bairro;
    private Responsavel responsavel;

    public PacienteResponseDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.cpf = paciente.getCpf();
        this.email = paciente.getEmail();
        this.nome = paciente.getNome();
        this.telefone = paciente.getTelefone();
        this.dataNascimento = paciente.getDataNascimento();
        this.estado = paciente.getEstado();
        this.cidade = paciente.getCidade();
        this.bairro = paciente.getBairro();
        this.responsavel = paciente.getResponsavel();
    }
}