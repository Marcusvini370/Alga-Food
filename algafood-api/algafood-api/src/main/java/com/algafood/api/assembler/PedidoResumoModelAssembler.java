package com.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.api.dto.PedidoResumoDTO;
import com.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public PedidoResumoDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }
    
    public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
    
}