package com.algafood.domain.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Setter
@Getter
public class PermissaoDTO extends RepresentationModel<PermissaoDTO> {

    private Long id;
    private String nome;
    private String descricao;

}
