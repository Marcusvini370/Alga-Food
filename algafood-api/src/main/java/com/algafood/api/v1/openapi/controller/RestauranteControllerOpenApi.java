package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algafood.api.v1.model.RestauranteDTO;
import com.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista restaurantes")
    @Parameters({
            @Parameter(description = "Nome da projeção de pedidos", example = "apenas-nome", name = "projecao",
                    in = ParameterIn.QUERY, required = false)
    })
    // @JsonView(RestauranteView.resumo.class)
    CollectionModel<RestauranteBasicoDTO> listar();

    @Operation(summary = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteBasicoDTO> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    RestauranteDTO buscar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Cadastra um restaurante")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),})
    RestauranteDTO adicionar(
            @RequestBody( description = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza um restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    RestauranteDTO atualizar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,

            @RequestBody( description = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteInput);

    @Operation(summary = "Ativa um restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Inativa um restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Ativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")})
    void ativarMultiplos(
            @Parameter(name = "corpo", description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Inativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")})
    void inativarMultiplos(
            @Parameter(name = "corpo", description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Abre um restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> abrir(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Fecha um restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> fechar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}