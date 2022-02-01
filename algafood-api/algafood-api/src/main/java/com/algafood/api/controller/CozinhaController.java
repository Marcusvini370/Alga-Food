package com.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algafood.api.assembler.CozinhaInputDisassembler;
import com.algafood.api.assembler.CozinhaModelAssembler;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.dto.CozinhaDTO;
import com.algafood.domain.model.dto.input.CozinhaInput;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;   

	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		List<CozinhaDTO> cozinhasDTO = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		
		Page<CozinhaDTO> cozinhaDtoPage = new PageImpl<>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
		
		return cozinhaDtoPage;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		return cozinhaModelAssembler.toModel(cadastroCozinha.BuscarOuFalhar(cozinhaId));

	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) { // o @RequestBody vai receber o corpo da requisição
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));

	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinhaAtual = cadastroCozinha.BuscarOuFalhar(cozinhaId);

		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); 
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);													

		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

	}

	/*
	 * @DeleteMapping("/{cozinhaId}") public ResponseEntity<?> remover(@PathVariable
	 * Long cozinhaId) { try { cadastroCozinha.excluir(cozinhaId); return
	 * ResponseEntity.noContent().build();
	 * 
	 * } catch (EntidadeNaoEncontradaExcpetion e) { return
	 * ResponseEntity.notFound().build();
	 * 
	 * 
	 * } catch (EntidadeEmusoExcpetion e) { return
	 * ResponseEntity.status(HttpStatus.CONFLICT).build(); } }
	 */

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

		cadastroCozinha.excluir(cozinhaId);

	}
}
