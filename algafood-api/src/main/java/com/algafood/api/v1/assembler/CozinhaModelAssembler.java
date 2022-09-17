package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.CozinhaController;
import com.algafood.api.v1.model.CozinhaDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaDTO.class);

    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
        }


        return cozinhaModel;
    }


}        
