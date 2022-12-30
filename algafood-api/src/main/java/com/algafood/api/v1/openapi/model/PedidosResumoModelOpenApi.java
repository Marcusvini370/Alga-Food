package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.PedidoResumoDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
public class PedidosResumoModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Data
    public class PedidosResumoEmbeddedModelOpenApi {

        private List<PedidoResumoDTO> pedidos;

    }

}