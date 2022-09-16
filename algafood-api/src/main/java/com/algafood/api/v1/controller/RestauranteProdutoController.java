package com.algafood.api.v1.controller;

import com.algafood.api.v1.AlgaLinks;
import com.algafood.api.v1.assembler.ProdutoInputDisassembler;
import com.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algafood.api.v1.model.ProdutoDTO;
import com.algafood.api.v1.model.input.ProdutoInput;
import com.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algafood.core.security.CheckSecurity;
import com.algafood.domain.model.Produto;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.ProdutoRepository;
import com.algafood.domain.service.CadastroProdutoService;
import com.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId,
                                              @RequestParam(required = false, defaultValue = "false") boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByProdutos(restaurante);
        }


        return produtoModelAssembler.toCollectionModel(todosProdutos)
                .add(algaLinks.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
                                @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return produtoModelAssembler.toModel(produtoAtual);
    }
}   
