package com.algafood.api.assembler;

import com.algafood.api.dto.FormaPagamentoDTO;
import com.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()            //collection é tanto list como set
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }

}
