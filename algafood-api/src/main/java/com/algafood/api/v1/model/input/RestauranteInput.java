package com.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteInput {

    @Schema(example = "Thai Gourmet", required = true)
    @NotBlank
    private String nome;

    @Schema(example = "12.00", required = true)
    @NotNull
    @PositiveOrZero // @DecimalMin("0")
    private BigDecimal taxaFrete;

    //@JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid
    @NotNull
    private CozinhaIdinput cozinha;

    private boolean ativo;

    @Valid
    @NotNull
    private EnderecoInput endereco;


}
