package com.algafood.api.dto;

import java.math.BigDecimal;

import com.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView(value = { RestauranteView.resumo.class , RestauranteView.apenasNome.class})
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	@JsonView(value = { RestauranteView.resumo.class , RestauranteView.apenasNome.class})
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	@JsonView(RestauranteView.resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	private EnderecoDTO endereco;
	private Boolean aberto;
	
	
}
