package com.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algafood.api.assembler.CidadeInputDisassembler;
import com.algafood.api.assembler.CidadeModelAssembler;
import com.algafood.domain.exception.EstadoNaoEncontradoExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.dto.CidadeDTO;
import com.algafood.domain.model.dto.input.CidadeInput;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeModelAssembler.toModel(cadastroCidade.BuscarOuFalhar(cidadeId));
	}

	/*
	 * @PostMapping public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
	 * try { cidade = cadastroCidade.salvar(cidade);
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED) .body(cidade); } catch
	 * (EntidadeNaoEncontradaExcpetion e) { return ResponseEntity.badRequest()
	 * .body(e.getMessage()); }
	 * 
	 * }
	 */

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			
			//Conversão do CidadeInput para Cidade
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	/*
	 * @PutMapping("/{cidadeId}") public ResponseEntity<?> atualizar(@PathVariable
	 * Long cidadeId,
	 * 
	 * @RequestBody Cidade cidade) {
	 * 
	 * try { Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
	 * 
	 * if (cidadeAtual.isPresent()) { BeanUtils.copyProperties(cidade,
	 * cidadeAtual.get(), "id");
	 * 
	 * Cidade cidadeSalvo = cadastroCidade.salvar(cidadeAtual.get()); return
	 * ResponseEntity.ok(cidadeSalvo); }
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * } catch (EntidadeNaoEncontradaExcpetion e) { return
	 * ResponseEntity.badRequest() .body(e.getMessage()); } }
	 */

	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		

		try {
			
			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}
