package com.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Transactional
	public void conformar(String codogioPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);

		pedido.confirmar();

	}

	@Transactional
	public void cancelar(String codogioPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);

		pedido.cancelar();
	}

	@Transactional
	public void entregar(String codogioPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);
		pedido.entregar();
	}

}
