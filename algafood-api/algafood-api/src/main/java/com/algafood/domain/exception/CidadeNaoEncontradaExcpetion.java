package com.algafood.domain.exception;

public class CidadeNaoEncontradaExcpetion extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaExcpetion(String mensagem) {
		super(mensagem);
	}

	public CidadeNaoEncontradaExcpetion(Long cidadeId) {
		this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
	}

}
