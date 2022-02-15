package com.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteInput {
	
	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "12.00", required = true)
	@NotNull
	@PositiveOrZero // @DecimalMin("0")
	private BigDecimal taxaFrete;
	
	//@JsonIgnoreProperties(value = "nome", allowGetters = true)
	@Valid
	@NotNull
	private CozinhaIdinput cozinha;
	
	private boolean ativo;
	
	@Valid
	@NotNull
	private EnderecoInput endereco;
	
	

}
