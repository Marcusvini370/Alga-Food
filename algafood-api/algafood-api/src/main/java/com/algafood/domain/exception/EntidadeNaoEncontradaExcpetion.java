package com.algafood.domain.exception;

public class EntidadeNaoEncontradaExcpetion extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaExcpetion(String mensagem) {
		super(mensagem);
	}

}
