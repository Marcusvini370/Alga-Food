package com.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

    @Schema(example = "1")
    //@JsonView(RestauranteView.resumo.class)
    private Long id;

    @Schema(example = "Brasileira")
    //@JsonView(RestauranteView.resumo.class)
    private String nome;

}
