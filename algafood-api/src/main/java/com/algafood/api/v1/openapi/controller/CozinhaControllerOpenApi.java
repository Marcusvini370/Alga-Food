package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.CozinhaDTO;
import com.algafood.api.v1.model.input.CozinhaInput;
import com.algafood.core.openapi.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Cozinhas")
@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista as cozinhas com paginação")
    PagedModel<CozinhaDTO> listar(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaDTO buscar(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);

    @Operation(summary = "Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    CozinhaDTO adicionar(
            @RequestBody( description = "Representação de uma nova cozinha", required = true)
            CozinhaInput cozinhaInput);

    @Operation(summary = "Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaDTO atualizar(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId,

            @RequestBody(description = "Representação de uma cozinha com os novos dados", required = true)
            CozinhaInput cozinhaInput);

    @Operation(summary = "Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
}