package com.algafood.domain.model.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class SenhaInput extends UsuarioInput {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;
}
