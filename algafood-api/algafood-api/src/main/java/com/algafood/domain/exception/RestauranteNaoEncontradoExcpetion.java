package com.algafood.domain.exception;

public class RestauranteNaoEncontradoExcpetion extends EntidadeNaoEncontradaExcpetion{


	private static final long serialVersionUID = 1L;
	
	
	public RestauranteNaoEncontradoExcpetion(String mensagem) {
		super(mensagem);
 	}
	
	public RestauranteNaoEncontradoExcpetion(Long restauranteId) {
        this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }   
	
	}
