package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.RestauranteBasicoDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class RestaurantesEmbeddedModelOpenApi {

        private List<RestauranteBasicoDTO> restaurantes;

    }
}
