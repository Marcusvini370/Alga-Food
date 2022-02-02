package com.algafood.api.dto.input;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CozinhaIdinput {
	
	@NotNull
	private Long id;

}
