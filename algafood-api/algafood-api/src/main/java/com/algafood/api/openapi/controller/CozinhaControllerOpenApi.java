package com.algafood.api.openapi.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algafood.api.dto.CozinhaDTO;
import com.algafood.api.dto.input.CozinhaInput;
import com.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
     Page<CozinhaDTO> listar(Pageable pageable);
    
    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(responseCode =  "400", description  = "ID da cozinha inválido", 
        		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
        content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
    })
     CozinhaDTO buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required =  true)
            Long cozinhaId);
    
    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
     CozinhaDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required =  true)
            CozinhaInput cozinhaInput);
    
    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
        content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
    })
     CozinhaDTO atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required =  true)
            Long cozinhaId,
            
            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true)
            CozinhaInput cozinhaInput);
    
    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
        content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
    })
     void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required =  true)
            Long cozinhaId);   
}