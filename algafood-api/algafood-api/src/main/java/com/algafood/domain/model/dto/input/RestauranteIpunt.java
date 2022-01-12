package com.algafood.domain.model.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteIpunt {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@PositiveOrZero // @DecimalMin("0")
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdinput cozinha;
	

}
