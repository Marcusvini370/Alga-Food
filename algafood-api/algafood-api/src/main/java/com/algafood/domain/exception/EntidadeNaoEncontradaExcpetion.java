package com.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaExcpetion extends NegocioException{


	private static final long serialVersionUID = 1L;
	
	
	public EntidadeNaoEncontradaExcpetion(String mensagem) {
		super(mensagem);
 	}
	
	}
