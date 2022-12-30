package com.algafood.api.v1.openapi.model;

import com.algafood.api.v1.model.CidadeDTO;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelOpenApi {

	private CidadeEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@Data
	public class CidadeEmbeddedModelOpenApi {
		
		private List<CidadeDTO> cidades;
		
	}
	
}