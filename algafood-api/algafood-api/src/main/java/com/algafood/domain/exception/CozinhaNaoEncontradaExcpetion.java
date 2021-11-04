package com.algafood.domain.exception;

public class CozinhaNaoEncontradaExcpetion extends EntidadeNaoEncontradaExcpetion{


	private static final long serialVersionUID = 1L;
	
	
	public CozinhaNaoEncontradaExcpetion(String mensagem) {
		super(mensagem);
 	}
	
	public CozinhaNaoEncontradaExcpetion(Long cozinhaId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
    } 
	
	}
