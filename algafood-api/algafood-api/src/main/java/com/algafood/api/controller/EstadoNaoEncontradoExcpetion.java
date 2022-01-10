package com.algafood.api.controller;

import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;

public class EstadoNaoEncontradoExcpetion extends EntidadeNaoEncontradaExcpetion {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoExcpetion(String mensagem) {
		super(mensagem);
	}

}
