package com.algafood.api.v2.openapi.model;

import com.algafood.api.v2.model.CozinhaModelV2;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
public class CozinhasModelV2OpenApi {
    
    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV2OpenApi page;
    
    @Data
    public class CozinhasEmbeddedModelOpenApi {
        
        private List<CozinhaModelV2> cozinhas;
        
    }
    
}