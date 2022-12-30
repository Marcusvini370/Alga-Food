package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.GrupoDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @Data
    public class GruposEmbeddedModelOpenApi {
        
        private List<GrupoDTO> grupos;
        
    }   
}