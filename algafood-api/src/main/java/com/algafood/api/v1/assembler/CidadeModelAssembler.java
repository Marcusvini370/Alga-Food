package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.CidadeController;
import com.algafood.api.v1.model.CidadeDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeDTO.class);

    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeDTO);

        if (algaSecurity.podeConsultarCidades()) {
            cidadeDTO.add(algaLinks.linkToCidades("cidades"));
        }

        if (algaSecurity.podeConsultarEstados()) {
            cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidadeDTO.getEstado().getId()));
        }

        return cidadeDTO;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarCidades()) {
            collectionModel.add(algaLinks.linkToCidades());
        }

        return collectionModel;

    }

	/*public List<CidadeDTO> toCollectionModel(List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
	} */

}
