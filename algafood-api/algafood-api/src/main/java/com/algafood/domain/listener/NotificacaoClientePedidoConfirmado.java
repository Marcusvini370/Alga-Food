package com.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algafood.domain.event.PedidoConfirmadoEvent;
import com.algafood.domain.model.Pedido;
import com.algafood.domain.service.EnvioEmailService;
import com.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmado {

	@Autowired
	private EnvioEmailService envioEmail;

	@EventListener // marca um evento como listener, quando o corre um evento de confirmarPedido
					// ele dispara.
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

		Pedido pedido = event.getPedido();
		// Montagem do email a ser enviado
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.variavel("pedido", pedido).corpo("pedido-confirmado.html")
				.destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);

	}

}
