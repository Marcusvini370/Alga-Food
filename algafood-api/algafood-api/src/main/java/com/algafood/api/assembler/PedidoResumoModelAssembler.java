package com.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.controller.PedidoController;
import com.algafood.api.controller.RestauranteController;
import com.algafood.api.controller.UsuarioController;
import com.algafood.api.dto.PedidoResumoDTO;
import com.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

    @Autowired
    private ModelMapper modelMapper;
    
    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }
    
    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
    	PedidoResumoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
        
        pedidoModel.getCliente().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        
        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());
        
        return pedidoModel;
    }
    
    
    
}