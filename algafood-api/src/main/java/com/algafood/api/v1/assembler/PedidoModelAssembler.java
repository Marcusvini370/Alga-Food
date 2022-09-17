package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.PedidoController;
import com.algafood.api.v1.model.PedidoDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {

            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmacao"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entrega"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelamento"));
            }

        }

        pedidoModel.getRestaurante().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(
                algaLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoModel.getFormaPagamento().add(
                algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoModel.getEnderecoEntrega().getCidade().add(
                algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoModel.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoModel;
    }


}