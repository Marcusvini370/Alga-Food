package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.EstadoDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @Data
    public class EstadosEmbeddedModelOpenApi {
        
        private List<EstadoDTO> estados;
        
    }
}