package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.AlgaLinks;
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
	
	@Autowired
	private AlgaLinks algaLinks;
    
	@Override
    public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		 
		 cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
		 
		 
		 return cozinhaModel;
    }
    
    
    
}        
