package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.domain.model.Produto;
import com.algafood.domain.model.dto.input.ProdutoInput;

@Component
public class ProdutoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
    
    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }   
}         