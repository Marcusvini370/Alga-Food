package com.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

    @Schema(example = "Espetinho de Cupim", required = true)
    @NotBlank
    private String nome;

    @Schema(example = "Acompanha farinha, mandioca e vinagrete", required = true)
    @NotBlank
    private String descricao;

    @Schema(example = "12.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @Schema(example = "true", required = true)
    @NotNull
    private Boolean ativo;

}           