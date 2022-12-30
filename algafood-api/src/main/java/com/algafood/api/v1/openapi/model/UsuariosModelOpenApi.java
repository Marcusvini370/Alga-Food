package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.UsuarioDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @Data
    public class UsuariosEmbeddedModelOpenApi {
        
        private List<UsuarioDTO> usuarios;
        
    }   
}