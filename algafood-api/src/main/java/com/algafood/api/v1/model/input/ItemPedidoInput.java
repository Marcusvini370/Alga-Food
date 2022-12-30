package com.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class ItemPedidoInput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long produtoId;

    @Schema(example = "2", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @Schema(example = "Menos picante, por favor")
    private String observacao;
}  
