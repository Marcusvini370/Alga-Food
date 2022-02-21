package com.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.AlgaLinks;
import com.algafood.api.controller.CidadeController;
import com.algafood.api.controller.FormaPagamentoController;
import com.algafood.api.controller.PedidoController;
import com.algafood.api.controller.RestauranteController;
import com.algafood.api.controller.RestauranteProdutoController;
import com.algafood.api.controller.UsuarioController;
import com.algafood.api.dto.PedidoDTO;
import com.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
	private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }
    
    @Override
    public PedidoDTO toModel(Pedido pedido) {
    	 PedidoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
         modelMapper.map(pedido, pedidoModel);
         
         pedidoModel.add(algaLinks.linkToPedidos());
         
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