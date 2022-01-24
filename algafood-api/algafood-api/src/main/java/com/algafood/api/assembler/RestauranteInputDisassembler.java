package com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.model.dto.input.RestauranteInput;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
	return modelMapper.map(restauranteInput, Restaurante.class);	
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// para evitar org.springframework.orm.jpa.JpaSystemException: identifier of an instance of 
		//com.algafood.domain.model.Cozinha was altered from 1 to 2; nested exception is 
		//org.hibernate.HibernateException: identifier of an instance of com.algafood.domain.model.Cozinha was altered from 1 to 2

		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
