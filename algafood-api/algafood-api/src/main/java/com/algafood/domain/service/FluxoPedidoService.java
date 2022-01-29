package com.algafood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Pedido;
import com.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Transactional
	public void conformar(Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new  NegocioException(
					String.format("Status do pedido %d não pode ser alterado de %s para %S", 
							pedido.getId(), pedido.getStatus().getDescricao(), 
							StatusPedido.CONFIRMADO.getDescricao()));
		}
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	

}
