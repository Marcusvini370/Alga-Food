package com.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.core.validation.ValidacaoException;
import com.algafood.domain.exception.CozinhaNaoEncontradaExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.model.dto.CozinhaDTO;
import com.algafood.domain.model.dto.RestauranteDTO;
import com.algafood.domain.model.dto.input.RestauranteIpunt;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private SmartValidator validator;

	@GetMapping
	public List<RestauranteDTO> listar() {
		return toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
		
		return toModel(restaurante);
	}

	

	/*
	 * @PostMapping public ResponseEntity<?> adicionar(@RequestBody Restaurante
	 * restaurante){ //<?> parametro coringa try { restaurante =
	 * cadastroRestaurante.salvar(restaurante);
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
	 * 
	 * }catch(EntidadeNaoEncontradaExcpetion e) { return
	 * ResponseEntity.badRequest().body(e.getMessage());
	 * 
	 * }
	 * 
	 * }
	 */

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteIpunt restauranteInput) {

		try {
				Restaurante restaurante = toDomainObject(restauranteInput); //Conversão do RestauranteInput para Restaurante
			
			return toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaExcpetion e) {
			throw new NegocioException(e.getMessage());
		}

	}

	/*
	 * @PutMapping("/{restauranteId}") public ResponseEntity<?>
	 * atualizar(@PathVariable Long restauranteId,
	 * 
	 * @RequestBody Restaurante restaurante){
	 * 
	 * try { Restaurante restauranteAtual =
	 * restauranteRepository.findById(restauranteId).orElse(null);
	 * 
	 * if(restauranteAtual != null) { BeanUtils.copyProperties(restaurante,
	 * restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
	 * "produtos");
	 * 
	 * Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual);
	 * 
	 * return ResponseEntity.ok(restauranteSalvo);
	 * 
	 * } return ResponseEntity.notFound().build();
	 * 
	 * 
	 * }catch(EntidadeNaoEncontradaExcpetion e) { return
	 * ResponseEntity.badRequest().body(e.getMessage()); } }
	 */

	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteIpunt restauranteInput) {
		
		try {
			Restaurante restaurante = toDomainObject(restauranteInput); //Conversão do RestauranteInput para Restaurante
			
			Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
					"produtos");
			
			return toModel(cadastroRestaurante.salvar(restauranteAtual));

		} catch (CozinhaNaoEncontradaExcpetion e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request, RestauranteIpunt restauranteInput) {

		Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);

		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");

		return  null;//atualizar(restauranteId, restauranteAtual);

	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

			camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	private RestauranteDTO toModel(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId()); 
		cozinhaDTO.setNome(restaurante.getCozinha().getNome()); 
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinha(cozinhaDTO);
		return restauranteDTO;
	}

	private List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
	
	private Restaurante toDomainObject(RestauranteIpunt restauranteInput) {
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha); // associa a cozinha intanciada ao restaurante
		
		return restaurante;
		
	}
	
}
