package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.ProdutoDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @Data
    public class ProdutosEmbeddedModelOpenApi {
        
        private List<ProdutoDTO> produtos;
        
    }    
}