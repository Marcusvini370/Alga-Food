package com.algafood.api.dto.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoIdInput {
	
	@ApiModelProperty(example = "1")
	@NotNull
	private Long id;

}
