package com.algafood.domain.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

    //@JsonView(RestauranteView.resumo.class)
    private Long id;

    //@JsonView(RestauranteView.resumo.class)
    private String nome;

}
