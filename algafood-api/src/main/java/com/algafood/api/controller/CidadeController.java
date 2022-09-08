package com.algafood.api.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CollectionModel<CidadeDTO> listar() {
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
