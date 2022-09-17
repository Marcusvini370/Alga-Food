package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.RestauranteProdutoController;
import com.algafood.api.v1.model.ProdutoDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.Produto;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    @SneakyThrows
    public ProdutoDTO toModel(Produto produto) {
       ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

            produtoModel.add(algaLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModel;
    }

}          
