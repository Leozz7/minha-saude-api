package com.tdeBack.minhaSaude.model;

import com.tdeBack.minhaSaude.enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Email
    String email;

    @NotBlank
    String nome;

    @Enumerated(EnumType.STRING)
    Tipo tipo;

    @NotBlank
    String senha;
}
