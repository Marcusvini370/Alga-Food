package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.model.PermissaoDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PermissaoDTO toModel(Permissao permissao) {
        PermissaoDTO permissaoModel = modelMapper.map(permissao, PermissaoDTO.class);
        return permissaoModel;
    }

    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoDTO> collectionModel
                = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToPermissoes());
        }

        return collectionModel;
    }
}
