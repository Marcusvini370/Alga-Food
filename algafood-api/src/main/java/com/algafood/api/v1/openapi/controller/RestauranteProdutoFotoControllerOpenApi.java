package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.FotoProdutoDTO;
import com.algafood.api.v1.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    FotoProdutoDTO atualizarFoto(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @Parameter(description = "ID do produto", example = "1", required = true)
            Long produtoId,

            FotoProdutoInput fotoProdutoInput,

            @Parameter(description = "Arquivo da foto do produto (máximo 4MB, apenas JPG e PNG)",
                    required = true)
            MultipartFile arquivo) throws IOException;

    @Operation(summary = "Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void excluir(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @Parameter(description = "ID do produto", example = "1", required = true)
            Long produtoId);

    @Operation(description = "Busca a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FotoProdutoDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "image/jpeg",schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<?> buscar(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true)
            Long produtoId,
            @Parameter(hidden = true, required = false)
            String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;


}
    