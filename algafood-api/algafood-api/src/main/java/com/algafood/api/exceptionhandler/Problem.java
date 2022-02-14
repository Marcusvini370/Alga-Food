package com.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "2022-02-14T16:39:13Z")	
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "https://algafood.com.br/recurso-nao-encontrado")	
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos")	
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
	private String userMessage;
	
	@ApiModelProperty("Objetos ou campos que geraram o erro")
	private List<Object> objects;

	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {

		@ApiModelProperty(example = "preco")
		private String name;
		
		@ApiModelProperty(example = "Opreço é obrigatório")
		private String userMessage;
	}
}
