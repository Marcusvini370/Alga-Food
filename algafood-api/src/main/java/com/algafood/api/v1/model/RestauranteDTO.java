package com.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> {

    @ApiModelProperty(example = "1")
    //@JsonView(value = {RestauranteView.resumo.class, RestauranteView.apenasNome.class})
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
   // @JsonView(value = {RestauranteView.resumo.class, RestauranteView.apenasNome.class})
    private String nome;

    @ApiModelProperty(example = "12.00")
   // @JsonView(RestauranteView.resumo.class)
    private BigDecimal taxaFrete;

   // @JsonView(RestauranteView.resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;


}
