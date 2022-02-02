package com.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.api.assembler.ProdutoInputDisassembler;
import com.algafood.api.assembler.ProdutoModelAssembler;
import com.algafood.api.dto.ProdutoDTO;
import com.algafood.api.dto.input.ProdutoInput;
import com.algafood.domain.model.Produto;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.ProdutoRepository;
import com.algafood.domain.service.CadastroProdutoService;
import com.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CadastroProdutoService cadastroProduto;
    
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;
    
    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;
    
    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
    		@RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(restauranteId);
        
        List<Produto> todosProdutos = null;
        
        if(incluirInativos) {
        	todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        }else {
        	todosProdutos = produtoRepository.findAtivosByProdutos(restaurante);
        }
        
        
        return produtoModelAssembler.toCollectionModel(todosProdutos);
    }
    
    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        return produtoModelAssembler.toModel(produto);
    }
    
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
    
    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        
        produtoAtual = cadastroProduto.salvar(produtoAtual);
        
        return produtoModelAssembler.toModel(produtoAtual);
    }   
}   
