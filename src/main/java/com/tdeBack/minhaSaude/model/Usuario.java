package com.tdeBack.minhaSaude.model;

import com.tdeBack.minhaSaude.enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String nome;

    @Enumerated(EnumType.STRING)
    Tipo tipo;

    @NotBlank
    String senha;
}
