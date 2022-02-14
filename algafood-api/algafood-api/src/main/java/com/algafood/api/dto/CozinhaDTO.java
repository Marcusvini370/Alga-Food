package com.algafood.api.dto;

import com.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.resumo.class)
	private Long id;

	@ApiModelProperty(example = "Brasileira")
	@JsonView(RestauranteView.resumo.class)
	private String nome;

}
