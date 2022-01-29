package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algafood.domain.model.Pedido;
import com.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }            
}        
