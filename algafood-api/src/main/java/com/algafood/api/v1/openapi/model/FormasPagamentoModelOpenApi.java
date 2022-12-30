package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.FormaPagamentoDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class FormasPagamentoEmbeddedModelOpenApi {

        private List<FormaPagamentoDTO> formasPagamento;

    }

}