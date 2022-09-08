package com.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    //@ApiModelProperty(value = "ID de cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Brasília")
    private String nome;

    private EstadoDTO estado;
}
