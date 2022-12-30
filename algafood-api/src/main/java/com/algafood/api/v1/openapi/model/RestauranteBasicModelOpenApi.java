package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.CozinhaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteBasicModelOpenApi {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

    @Schema(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

}
