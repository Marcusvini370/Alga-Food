package com.algafood.api.controller;

import com.algafood.domain.exception.EntidadeNaoEncontradaException;

public class EstadoNaoEncontradoExcpetion extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoExcpetion(String mensagem) {
        super(mensagem);
    }

}
