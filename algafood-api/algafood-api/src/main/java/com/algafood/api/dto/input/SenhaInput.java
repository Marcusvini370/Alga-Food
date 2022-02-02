package com.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class SenhaInput extends UsuarioInput {

	@NotBlank
	private String senhaAtual;

	@NotBlank
	private String novaSenha;
}
