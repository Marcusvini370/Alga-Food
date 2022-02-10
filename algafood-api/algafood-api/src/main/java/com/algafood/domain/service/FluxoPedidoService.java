package com.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.model.Pedido;
import com.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private EnvioEmailService envioEmail;

	@Transactional
	public void confirmar(String codogioPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codogioPedido);
		pedido.confirmar();

		// Montagem do email a ser enviado
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.variavel("pedido", pedido)
				.corpo("pedido-confirmado.html")
				.destinatario(pedido.getCliente().getEmail()).build();

		envioEmail.enviar(mensagem);
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
