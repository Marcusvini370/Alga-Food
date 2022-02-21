package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.AlgaLinks;
import com.algafood.api.controller.PedidoController;
import com.algafood.api.dto.PedidoResumoDTO;
import com.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }
    
    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
    	PedidoResumoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(algaLinks.linkToPedidos());
        
        pedidoModel.getCliente().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }
    
    
    
}