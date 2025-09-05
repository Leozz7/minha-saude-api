package com.tdeBack.minhaSaude.model;

import com.tdeBack.minhaSaude.enums.Tipo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Usuario {

    @Email
    String email;

    @NotBlank
    String nome;

    @Enumerated(EnumType.STRING)
    Tipo tipo;

    @NotBlank
    String senha;
}
