package com.algafood.api.v1.controller;

import com.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algafood.api.v1.model.EstadoDTO;
import com.algafood.api.v1.model.input.EstadoInput;
import com.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;
import com.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public CollectionModel<EstadoDTO> listar() {

        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {

        return estadoModelAssembler.toModel(cadastroEstado.BuscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.BuscarOuFalhar(estadoId);

        //BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));

    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }

}
