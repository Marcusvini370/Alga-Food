package com.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algafood.api.dto.FormaPagamentoDTO;
import com.algafood.api.dto.input.FormaPagamentoInput;
import com.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algafood.domain.model.FormaPagamento;
import com.algafood.domain.repository.FormaPagamentoRepository;
import com.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi{

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;
    
    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    
    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
    
    @GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {
    	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
    	
    	String eTag = "0";
    	OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
    	
    	if(dataUltimaAtualizacao != null) {
    		eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
    	}
    	//J?? temos condi????es e saber se continua ou n??o o processamento
    	if(request.checkNotModified(eTag)) {
    		return null;
    	}	
    	
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		
		List<FormaPagamentoDTO> formasPagamentosModel = formaPagamentoModelAssembler
				.toCollectionModel(todasFormasPagamentos);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				//.cachePrivate ?? cache armazenado em caches locais
				//.cachePulbic ?? armazenado em caches locais e compartilhados
				//.cacheControl(CacheControl.noCache()) //ao fazer cache vai precisar fazer valida????o como na etag
				.cacheControl(CacheControl.noStore()) //n??o armazena cache
				.body(formasPagamentosModel);
	}
    
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId,
            ServletWebRequest request) {
        
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        
        String eTag = "0";
        
        OffsetDateTime dataAtualizacao = formaPagamentoRepository
                .getDataAtualizacaoById(formaPagamentoId);
        
        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }
        
        if (request.checkNotModified(eTag)) {
            return null;
        }
        
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        
        FormaPagamentoDTO formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
        
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModel);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        
        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
        
        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }
    
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        
        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);
        
        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
    }
    
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamento.excluir(formaPagamentoId);	
    }   
}                
