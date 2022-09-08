package com.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    @Column(nullable = false)
    private String nome;

    //@Valid
    //@ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    //@NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estado;
}
