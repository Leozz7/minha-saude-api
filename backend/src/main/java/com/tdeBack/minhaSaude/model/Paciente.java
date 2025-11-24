package com.tdeBack.minhaSaude.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CPF(message = "cpf de paciente invalido")
    @NotBlank
    String cpf;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String nome;

    @NotBlank
    String telefone;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dataNascimento;

    String estado;

    @NotNull
    String cidade;

    @NotNull
    String bairro;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsavel_id")
    Responsavel responsavel;
}
