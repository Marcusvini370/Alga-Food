package com.algafood.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algafood.api.dto.EstadoDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {
	
	
	@ApiModelProperty(example = "Brasília")
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoDTO estado;

}
