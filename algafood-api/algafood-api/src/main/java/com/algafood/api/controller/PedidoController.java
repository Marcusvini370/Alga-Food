package com.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.api.assembler.PedidoInputDisassembler;
import com.algafood.api.assembler.PedidoModelAssembler;
import com.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algafood.api.dto.PedidoDTO;
import com.algafood.api.dto.PedidoResumoDTO;
import com.algafood.api.dto.input.PedidoInput;
import com.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algafood.core.data.PageableTranslator;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.filter.PedidoFilter;
import com.algafood.domain.model.Pedido;
import com.algafood.domain.model.Usuario;
import com.algafood.domain.repository.PedidoRepository;
import com.algafood.domain.service.EmissaoPedidoService;
import com.algafood.infracstruture.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableBiMap;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi{

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;
    
    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    
    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;
    
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    
    @GetMapping
    public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, 
            @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
        
        Page<Pedido> pedidosPage = pedidoRepository.findAll(
                PedidoSpecs.usandoFiltro(filtro), pageable);
        
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }
    
    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        
        return pedidoModelAssembler.toModel(pedido);
    }      
    
    @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
    
    private Pageable traduzirPageable(Pageable apiPageable) {
    	var mapeamento = ImmutableBiMap.of(
    			"codigo", "codigo",
    			"restaurante.nome", "restaurante.nome",
    			"nomeCliente", "cliente.nome",
    			"valorTotal", "valorTotal"
    			);
    	
    	return PageableTranslator.translate(apiPageable, mapeamento);
    	
    }
    
}           