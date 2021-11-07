package com.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaExcpetion extends NegocioException{


	private static final long serialVersionUID = 1L;
	
	
	public EntidadeNaoEncontradaExcpetion(String mensagem) {
		super(mensagem);
 	}
	
	}
