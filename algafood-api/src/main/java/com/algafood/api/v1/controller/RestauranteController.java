package com.algafood.api.v1.controller;

import com.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algafood.api.v1.model.RestauranteDTO;
import com.algafood.api.v1.model.input.RestauranteInput;
import com.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algafood.core.security.CheckSecurity;
import com.algafood.domain.exception.CidadeNaoEncontradaExcpetion;
import com.algafood.domain.exception.CozinhaNaoEncontradaExcpetion;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.exception.RestauranteNaoEncontradoExcpetion;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.domain.service.CadastroRestauranteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;



    @CheckSecurity.Restaurantes.PodeEditar
    @GetMapping
    // @JsonView(RestauranteView.resumo.class)
    public CollectionModel<RestauranteBasicoDTO> listar() {
        return restauranteBasicoModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    // @JsonView(RestauranteView.apenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteBasicoDTO> listarApenasNomes() {
        return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput); // Conversão do
            // RestauranteInput
            // para
            // Restaurante

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaExcpetion | CidadeNaoEncontradaExcpetion e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            // Restaurante restaurante =
            // restauranteInputDisassemble.toDomainObject(restauranteInput); //Conversão do
            // RestauranteInput para Restaurante

            Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            // BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
            // "formasPagamento", "endereco", "dataCadastro",
            // "produtos");

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaExcpetion | CidadeNaoEncontradaExcpetion e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/ativacoes")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            cadastroRestaurante.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoExcpetion e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/ativacoes")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            cadastroRestaurante.inativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoExcpetion e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
