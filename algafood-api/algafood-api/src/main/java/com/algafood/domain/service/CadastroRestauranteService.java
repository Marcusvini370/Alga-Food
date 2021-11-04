package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.exception.RestauranteNaoEncontradoExcpetion;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.repository.RestauranteRepository;


@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de cozinha com código %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new RestauranteNaoEncontradoExcpetion(
						String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, cozinhaId)));
		
		
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	
	}
	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoExcpetion(String.format(
						MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId))); 
	}
}
