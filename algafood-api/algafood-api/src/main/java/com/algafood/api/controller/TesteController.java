package com.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(String nome){
		return cozinhaRepository.findByNomeContaining(nome); 
	}
	
	@GetMapping("/cozinhas/exists")
	public Boolean cozinhasExists(String nome){
		return cozinhaRepository.existsByNome(nome); 
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restarantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal); 
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restarantesPorNome(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNome(nome, cozinhaId); 
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> RestaurantePrimeiroPorNome(String nome){
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome); 
	}
	
	@GetMapping("/restaurantes/dois-por-nome")
	public List<Restaurante> restarantesTop2PorNomes(String nome){
		return restauranteRepository.findTop2BynomeContaining(nome); 
	}
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restarantesCountCozinha(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId); 
	}
	

}
