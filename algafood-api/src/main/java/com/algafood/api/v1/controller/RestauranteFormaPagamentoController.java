package com.algafood.api.v1.controller;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algafood.api.v1.model.FormaPagamentoDTO;
import com.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algafood.core.security.AlgaSecurity;
import com.algafood.core.security.CheckSecurity;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoDTO> formasPagamentoModel
                = formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks();

        formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));

            formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
                formaPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamentoDessasociacao(
                        restauranteId, formaPagamentoModel.getId(), "desassociar"));
            });
        }

        return formasPagamentoModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }


}
