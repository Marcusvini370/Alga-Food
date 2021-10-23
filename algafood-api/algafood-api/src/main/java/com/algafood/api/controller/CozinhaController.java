package com.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.exception.EntidadeEmusoExcpetion;
import com.algafood.domain.exception.EntidadeNaoEncontradaExcpetion;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.service.CadastroCozinhaService;


@RestController 
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
		
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) { 
		// o responseEntity representa uma resposta http onde pode ter  uma instância de cozinha nele
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		
		return ResponseEntity.notFound().build(); 
	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) { // o @RequestBody vai receber o corpo da requisição
		return cadastroCozinha.salvar(cozinha);
		
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha){
		
		Optional<Cozinha> cozinhaAual = cozinhaRepository.findById(cozinhaId);
		//cozinhaAual.setNome(cozinha.getNome());
		
		if(cozinhaAual.isPresent()) {
		BeanUtils.copyProperties(cozinha, cozinhaAual.get(), "id"); // copia os valores de cozinha e coloca dentro da cozinhaAtual, está ignoradano a cópia do id
		
		Cozinha cozinhaSalva = 	cadastroCozinha.salvar(cozinhaAual.get());
		
				return ResponseEntity.ok(cozinhaSalva);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaExcpetion e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmusoExcpetion e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}


