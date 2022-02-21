package com.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.controller.CozinhaController;
import com.algafood.api.dto.CozinhaDTO;
import com.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{

    public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
		
	}

	@Autowired
    private ModelMapper modelMapper;
    
	@Override
    public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		 
		 cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
		 
		 
		 return cozinhaModel;
    }
    
    
    
}        
