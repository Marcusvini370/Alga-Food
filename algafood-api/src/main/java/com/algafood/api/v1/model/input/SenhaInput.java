package com.algafood.api.v1.model.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class SenhaInput extends UsuarioInput {

    @Schema(example = "123", required = true)
    @NotBlank
    private String senhaAtual;

    @Schema(example = "123", required = true)
    @NotBlank
    private String novaSenha;
}
