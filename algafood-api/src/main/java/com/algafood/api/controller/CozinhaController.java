package com.algafood.api.controller;

import com.algafood.api.assembler.CozinhaInputDisassembler;
import com.algafood.api.assembler.CozinhaModelAssembler;
import com.algafood.api.dto.CozinhaDTO;
import com.algafood.api.dto.input.CozinhaInput;
import com.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaModelAssembler);

        return cozinhasPagedModel;
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
