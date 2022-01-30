package com.algafood.domain.model.dto;

import java.math.BigDecimal;

import com.algafood.domain.model.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	@JsonView(value = { RestauranteView.resumo.class , RestauranteView.apenasNome.class})
	private Long id;
	
	
	@JsonView(value = { RestauranteView.resumo.class , RestauranteView.apenasNome.class})
	private String nome;
	
	@JsonView(RestauranteView.resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	private EnderecoDTO endereco;
	private Boolean aberto;
	
	
}
