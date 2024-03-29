package com.algafood.api.v1.controller;

import com.algafood.api.ResourceUriHelper;
import com.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algafood.api.v1.model.CidadeDTO;
import com.algafood.api.v1.model.input.CidadeInput;
import com.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algafood.core.security.CheckSecurity;
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

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @CheckSecurity.Cidades.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @CheckSecurity.Cidades.PodeConsultar
    @Override
    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cadastroCidade.BuscarOuFalhar(cidadeId));
    }

    @CheckSecurity.Cidades.PodeEditar
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

    @CheckSecurity.Cidades.PodeEditar
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

    @CheckSecurity.Cidades.PodeEditar
    @Override
    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}
