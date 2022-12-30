package com.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class GrupoInput {

    @Schema(example = "Gerente", required = true)
    @NotBlank
    private String nome;

}  