package com.algafood.domain.service;

import com.algafood.domain.model.Pedido;
import com.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codogioPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codogioPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codogioPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);
        pedido.entregar();
    }

}
