package com.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.resumo.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestauranteView.resumo.class)
    private String nome;

}
