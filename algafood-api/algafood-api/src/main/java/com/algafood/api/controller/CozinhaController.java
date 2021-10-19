package com.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algafood.api.model.CozinhasXmlWrapper;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;


@RestController 
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}
	
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) { 
		// o responseEntity representa uma resposta http onde pode ter  uma instância de cozinha nele
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		
		return ResponseEntity.notFound().build(); 
	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) { // o @RequestBody vai receber o corpo da requisição
		return cozinhaRepository.salvar(cozinha);
		
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha){
		
		Cozinha cozinhaAual = cozinhaRepository.buscar(cozinhaId);
		//cozinhaAual.setNome(cozinha.getNome());
		
		if(cozinhaAual != null) {
		BeanUtils.copyProperties(cozinha, cozinhaAual, "id"); // copia os valores de cozinha e coloca dentro da cozinhaAtual, está ignoradano a cópia do id
		
		cozinhaRepository.salvar(cozinhaAual);
		
				return ResponseEntity.ok(cozinhaAual);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		try {
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if(cozinha != null) {
		cozinhaRepository.remover(cozinha);
		return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
		}catch(DataIntegrityViolationException e) {
			return  ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
		
}
