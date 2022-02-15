package com.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoDTO {

	@ApiModelProperty(example = "1")
    private Long id;
	
	@ApiModelProperty(example = "Cartão de crédito")
    private String descricao;
    
}    
