package com.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Brasília")
    private String nome;

    private EstadoDTO estado;
}
