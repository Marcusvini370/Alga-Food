package com.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	 @GetMapping
		public List<Restaurante> listar() {
			return restauranteRepository.findAll();
		}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId){
		return cadastroRestaurante.BuscarOuFalhar(restauranteId);
	}
	
	/*
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){  //<?> parametro coringa
			try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
			}catch(EntidadeNaoEncontradaExcpetion e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		
	}
	*/
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		return cadastroRestaurante.salvar(restaurante);
	}
	
	
	/*
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante){
			
			try {
			Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);
		
			if(restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",  "produtos");
				
				Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual);
				
				return ResponseEntity.ok(restauranteSalvo);
				
			}
			return ResponseEntity.notFound().build();
			
			
			}catch(EntidadeNaoEncontradaExcpetion e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}*/
	
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, 
				"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		
		return cadastroRestaurante.salvar(restauranteAtual);
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos){
		
		Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);

		
		
			merge(campos, restauranteAtual);
				return atualizar(restauranteId, restauranteAtual);
		
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade,  valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true); //torna a variavel acessivel nome taxa_frete por eles serem privados
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
			
		});
	}
	
}
