package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.model.dto.input.RestauranteIpunt;

@Component
public class RestauranteInputDisassemble {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteIpunt restauranteInput) {
		
	return modelMapper.map(restauranteInput, Restaurante.class);
		
	}

}
