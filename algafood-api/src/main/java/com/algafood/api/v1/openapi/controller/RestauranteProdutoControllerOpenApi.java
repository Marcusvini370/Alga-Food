package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.ProdutoDTO;
import com.algafood.api.v1.model.input.ProdutoInput;
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

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<ProdutoDTO> listar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @Parameter(description = "Indica se deve ou não incluir produtos inativos no resultado da listagem",
                    example = "false", required = false)
            boolean incluirInativos);

    @Operation(summary = "Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoDTO buscar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @Parameter(description = "ID do produto", example = "1", required = true)
            Long produtoId);

    @Operation(summary = "Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoDTO adicionar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @RequestBody(description = "Representação de um novo produto", required = true)
            ProdutoInput produtoInput);

    @Operation(summary = "Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoDTO atualizar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @Parameter(description = "ID do produto", example = "1", required = true)
            Long produtoId,

            @RequestBody(description = "Representação de um produto com os novos dados",
                    required = true)
            ProdutoInput produtoInput);
}