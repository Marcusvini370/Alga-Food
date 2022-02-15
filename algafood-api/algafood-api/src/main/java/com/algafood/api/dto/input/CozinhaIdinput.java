package com.algafood.api.dto.input;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CozinhaIdinput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

}
