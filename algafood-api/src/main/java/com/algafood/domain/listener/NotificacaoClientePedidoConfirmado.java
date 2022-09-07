package com.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algafood.domain.event.PedidoConfirmadoEvent;
import com.algafood.domain.model.Pedido;
import com.algafood.domain.service.EnvioEmailService;
import com.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmado {

    @Autowired
    private EnvioEmailService envioEmail;

    // pode escolher a fase específica da transação para disparar o event
    @TransactionalEventListener
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
