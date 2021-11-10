package com.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(value = Include.NON_NULL) // mostre apenas os que não estão nulos
@Getter
@Builder
@AllArgsConstructor
public class Problem {
	
	private LocalDateTime timestamp;
	private Integer status;
	private String type;
	private String title;
	private String detail;
	

	private String userMessage;
	
	
	
	
	
}
