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
import com.algafood.api.dto.CidadeDTO;
import com.algafood.api.dto.input.CidadeInput;
import com.algafood.api.exceptionhandler.Problem;
import com.algafood.domain.exception.EstadoNaoEncontradoExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Api(tags = "Cidades") 
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

	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "400", description = "ID da cidade é inválido", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(
			@ApiParam(value = "ID de uma cidade") 
			@PathVariable Long cidadeId) {
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

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Cidade cadastrada")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(
			@RequestBody @Valid CidadeInput cidadeInput) {
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

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade Atualizada", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(
			@ApiParam(value = "ID de uma cidade") 
			@PathVariable Long cidadeId, 
			@RequestBody @Valid CidadeInput cidadeInput) {
		

		try {
			
			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			//BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradoExcpetion e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade Excluída", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@DeleteMapping("/{cidadeId}")
	public void remover(
						@ApiParam(value = "ID de uma cidade", example = "1")
						@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}
