package com.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.model.dto.input.RestauranteIpunt;

@Component
public class RestauranteInputDisassemble {

public Restaurante toDomainObject(RestauranteIpunt restauranteInput) {
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha); // associa a cozinha intanciada ao restaurante
		
		return restaurante;
		
	}

}
