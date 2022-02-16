package com.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

import com.algafood.api.ResourceUriHelper;
import com.algafood.api.assembler.CidadeInputDisassembler;
import com.algafood.api.assembler.CidadeModelAssembler;
import com.algafood.api.dto.CidadeDTO;
import com.algafood.api.dto.input.CidadeInput;
import com.algafood.api.openapi.controller.CidadeControllerOpenApi;
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
		
		CidadeDTO cidadeDTO = cidadeModelAssembler.toModel(cadastroCidade.BuscarOuFalhar(cidadeId));
		
		cidadeDTO.add(linkTo(CidadeController.class)
				.slash(cidadeDTO.getId()).withSelfRel());
		
		//cidadeDTO.add(Link.of("http://localhost:8080/cidades/1"));
//		cidadeModel.add(Link.of("http://localhost:8080/cidades/1", IanaLinkRelations.SELF));

//		cidadeModel.add(Link.of("http://localhost:8080/cidades/", IanaLinkRelations.COLLECTION));
//		cidadeDTO.add(Link.of("http://localhost:8080/cidades", "cidades"));
		
		cidadeDTO.add(linkTo(CidadeController.class)
				.withRel("cidades"));

//		cidadeDTO.getEstado().add(Link.of("http://localhost:8080/estados/1"));
		
		cidadeDTO.getEstado().add(linkTo(EstadoController.class)
				.slash(cidadeDTO.getEstado().getId()).withSelfRel());
		
		return cidadeDTO;
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {

			// Conversão do CidadeInput para Cidade
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			CidadeDTO cidadeDTO = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
			
			ResourceUriHelper.addUriResponseHeader(cidadeDTO.getId());

			return cidadeDTO;
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
