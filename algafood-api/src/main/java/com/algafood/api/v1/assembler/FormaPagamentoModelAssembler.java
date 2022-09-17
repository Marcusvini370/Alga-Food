package com.algafood.api.v1.assembler;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.controller.FormaPagamentoController;
import com.algafood.api.v1.model.FormaPagamentoDTO;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }


    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoModel =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }

        return formaPagamentoModel;
    }


    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(algaLinks.linkToFormasPagamento());
        }

        return collectionModel;
    }

/*    public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()            //collection é tanto list como set
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }*/

}
