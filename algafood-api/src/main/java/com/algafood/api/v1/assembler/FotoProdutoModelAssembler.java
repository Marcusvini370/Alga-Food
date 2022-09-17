package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algafood.api.v1.model.FotoProdutoDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler  extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    public FotoProdutoDTO toModel(FotoProduto foto) {
        FotoProdutoDTO fotoProdutoModel = modelMapper.map(foto, FotoProdutoDTO.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(algaLinks.linkToFotoProduto(
                    foto.getRestauranteId(), foto.getProduto().getId()));

            fotoProdutoModel.add(algaLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }


}        
