package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.FormaPagamentoDTO;
import com.algafood.api.v1.model.input.FormaPagamentoInput;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = "Formas de pagamento")
@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(@Parameter(hidden = true) ServletWebRequest request);

    @Operation(summary = "Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<FormaPagamentoDTO> buscar(
            @Parameter(description = "ID de uma forma de pagamento", example = "1")
            Long formaPagamentoId,

            ServletWebRequest request);

    @Operation(summary = "Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"),
    })
    FormaPagamentoDTO adicionar(
            @RequestBody(description = "Representação de uma nova forma de pagamento")
            FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problem.class)))
    })
    FormaPagamentoDTO atualizar(
            @Parameter(description = "ID de uma forma de pagamento", example = "1")
            Long formaPagamentoId,

            @RequestBody(description = "Representação de uma forma de pagamento com os novos dados")
            FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problem.class)))
    })
    void remover(Long formaPagamentoId);
}