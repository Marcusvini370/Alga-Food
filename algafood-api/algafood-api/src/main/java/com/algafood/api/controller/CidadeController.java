package com.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.algafood.api.controller.openapi.CidadeControllerOpenApi;
import com.algafood.api.dto.CidadeDTO;
import com.algafood.api.dto.input.CidadeInput;
import com.algafood.domain.exception.EstadoNaoEncontradoExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Override
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeModelAssembler.toModel(cadastroCidade.BuscarOuFalhar(cidadeId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {

			// Conversão do CidadeInput para Cidade
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			// BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}
