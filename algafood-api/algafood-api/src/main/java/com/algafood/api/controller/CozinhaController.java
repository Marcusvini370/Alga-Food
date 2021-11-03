package com.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	public Cozinha buscar(@PathVariable Long cozinhaId) { 
		return cadastroCozinha.BuscarOuFalhar(cozinhaId);
	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) { // o @RequestBody vai receber o corpo da requisição
		return cadastroCozinha.salvar(cozinha);
		
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha){
		
		Cozinha cozinhaAtual = cadastroCozinha.BuscarOuFalhar(cozinhaId);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); // copia os valores de cozinha e coloca dentro da cozinhaAtual, está ignoradano a cópia do id
		
		return	cadastroCozinha.salvar(cozinhaAtual);
		
		}
		
	
	/*
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaExcpetion e) {
			return ResponseEntity.notFound().build();
			
			
		} catch (EntidadeEmusoExcpetion e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	*/
	
	
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		
			cadastroCozinha.excluir(cozinhaId);				
		
	}
}



