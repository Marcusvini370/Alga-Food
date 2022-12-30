package com.algafood.api.v2.openapi.model;

import com.algafood.api.v2.model.CidadeModelV2;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @Data
    public class CidadesEmbeddedModelOpenApi {
        
        private List<CidadeModelV2> cidades;
        
    }
    
}