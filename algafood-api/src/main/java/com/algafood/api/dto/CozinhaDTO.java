package com.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

    @ApiModelProperty(example = "1")
    //@JsonView(RestauranteView.resumo.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    //@JsonView(RestauranteView.resumo.class)
    private String nome;

}
