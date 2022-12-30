package com.algafood.api.v2.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputV2 {

    @Schema(example = "Brasileira", required = true)
    @NotBlank
    private String nomeCozinha;   
} 