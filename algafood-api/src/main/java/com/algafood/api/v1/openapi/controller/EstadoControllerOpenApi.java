package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.EstadoDTO;
import com.algafood.api.v1.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Estados")
@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Lista os estados")
    CollectionModel<EstadoDTO> listar();

    @Operation(summary = "Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do estado inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    EstadoDTO buscar(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @Operation(summary = "Cadastra um estado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado cadastrado"),
    })
    EstadoDTO adicionar(
            @RequestBody( description = "Representação de um novo estado", required = true)
            EstadoInput estadoInput);

    @Operation(summary = "Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    EstadoDTO atualizar(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            Long estadoId,

            @RequestBody( description = "Representação de um estado com os novos dados", required = true)
            EstadoInput estadoInput);

    @Operation(summary = "Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(
            @Parameter(description = "ID de um estado", example = "1", required = true)
            Long estadoId);
}        