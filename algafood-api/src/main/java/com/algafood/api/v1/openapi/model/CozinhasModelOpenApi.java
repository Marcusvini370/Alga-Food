package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.CozinhaDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
public class CozinhasModelOpenApi  {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Data
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaDTO> cozinhas;

    }

}
