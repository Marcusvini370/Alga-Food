package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.GrupoDTO;
import com.algafood.api.v1.model.input.GrupoInput;
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

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista os grupos")
    CollectionModel<GrupoDTO> listar();

    @Operation(summary = "Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID da grupo inválido", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
    })
    GrupoDTO buscar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            Long grupoId);

    @Operation(summary = "Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo cadastrado"),
    })
    GrupoDTO adicionar(
            @RequestBody( description = "Representação de um novo grupo", required = true)
            GrupoInput grupoInput);

    @Operation(summary = "Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
    })
    GrupoDTO atualizar(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            Long grupoId,

            @RequestBody(description = "Representação de um grupo com os novos dados", required = true)
            GrupoInput grupoInput);

    @Operation(summary = "Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo excluído"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
    })
    void remover(
            @Parameter(description = "ID de um grupo", example = "1", required = true)
            Long grupoId);
}