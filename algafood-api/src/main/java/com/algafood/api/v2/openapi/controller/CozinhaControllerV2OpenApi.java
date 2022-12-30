package com.algafood.api.v2.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v2.model.CozinhaModelV2;
import com.algafood.api.v2.model.input.CozinhaInputV2;
import com.algafood.core.openapi.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;


@Tag(name = "Cozinhas")
@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerV2OpenApi {

    @Operation(summary = "Lista as cozinhas com paginação")
    @PageableParameter
    PagedModel<CozinhaModelV2> listar(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelV2 buscar(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
    
    @Operation(summary = "Cadastra uma cozinha")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
    })
    CozinhaModelV2 adicionar(
            @Parameter(name = "corpo", description = "Representação de uma nova cozinha", required = true)
            CozinhaInputV2 cozinhaInput);
    
    @Operation(summary = "Atualiza uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelV2 atualizar(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId,
            
            @Parameter(name = "corpo", description = "Representação de uma cozinha com os novos dados",
                required = true)
            CozinhaInputV2 cozinhaInput);
    
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