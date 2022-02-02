package com.algafood.api.dto;

import com.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	@JsonView(RestauranteView.resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.resumo.class)
	private String nome;

}
