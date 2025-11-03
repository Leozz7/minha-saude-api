package com.tdeBack.minhaSaude.model;

import com.tdeBack.minhaSaude.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    String numeroCarteira;

    @Enumerated(EnumType.STRING)
    TipoPagamento tipoPagamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atendimento", nullable = false)
    Date dataAtendimento = new Date();

    @Column(name = "valor_total", nullable = false)
    Double valorTotal;

    @ManyToMany
    @JoinTable(
            name = "atendimento_procedimentos",
            joinColumns = @JoinColumn(name = "atendimento_id"),
            inverseJoinColumns = @JoinColumn(name = "procedimento_id")
    )
    List<Procedimento> procedimentos;

    @Transient
    List<Long> procedimentoIds;
}
