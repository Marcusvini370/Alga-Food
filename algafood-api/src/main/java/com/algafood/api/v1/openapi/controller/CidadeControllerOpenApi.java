package com.algafood.api.v1.openapi.controller;

import com.algafood.api.v1.model.CidadeDTO;
import com.algafood.api.v1.model.input.CidadeInput;
import com.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "400", description = "ID da cidade é inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada")
    })
    CidadeDTO adicionar(CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade Atualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") Long cidadeId, CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cidade Excluída",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(@ApiParam(value = "ID de uma cidade") Long cidadeId);

}