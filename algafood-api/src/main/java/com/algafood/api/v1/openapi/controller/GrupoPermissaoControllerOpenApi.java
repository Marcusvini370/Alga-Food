package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.PermissaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<PermissaoDTO> listar(
            @Parameter(description = "ID do grupo", example = "1", required = true)
            Long grupoId);

    @Operation(summary = "Desassociação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID do grupo", example = "1", required = true)
            Long grupoId,

            @Parameter(description= "ID da permissão", example = "1", required = true)
            Long permissaoId);

    @Operation(summary = "Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID do grupo", example = "1", required = true)
            Long grupoId,

            @Parameter(description = "ID da permissão", example = "1", required = true)
            Long permissaoId);
}        