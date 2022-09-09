package com.algafood.api.v2.controller;

import com.algafood.api.ResourceUriHelper;
import com.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algafood.api.v2.model.CidadeModelV2;
import com.algafood.api.v2.model.input.CidadeInputV2;
import com.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algafood.domain.exception.EstadoNaoEncontradoExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;
	
	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@GetMapping(value = "/{cidadeId}")
	public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.BuscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade = cadastroCidade.salvar(cidade);
			
			CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriResponseHeader(cidadeModel.getIdCidade());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping(value = "/{cidadeId}")
	public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			
			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
 // Não pode ser mapeado na mesma URL em um MediaType diferente, já que não aceita entrada e retorna void.
	@DeleteMapping(value = "/{cidadeId}", produces = {})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
	
}