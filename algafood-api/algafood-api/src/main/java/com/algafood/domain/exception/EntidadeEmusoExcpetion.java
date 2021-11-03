package com.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EntidadeEmusoExcpetion extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeEmusoExcpetion(String mensagem) {
		super(mensagem);
	}	
	
}
