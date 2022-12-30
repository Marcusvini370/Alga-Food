package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.PedidoDTO;
import com.algafood.api.v1.model.PedidoResumoDTO;
import com.algafood.api.v1.model.input.PedidoInput;
import com.algafood.core.openapi.PageableParameter;
import com.algafood.domain.filter.PedidoFilter;
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
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {

    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "clienteId",
                    description = "ID do cliente para filtro da pesquisa",
                    example = "1", schema = @Schema(type = "integer")),
            @Parameter(in = ParameterIn.QUERY, name = "restauranteId",
                    description = "ID do restaurante para filtro da pesquisa",
                    example = "1", schema = @Schema(type = "integer")),
            @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio",
                    description = "Data/hora de criação inicial para filtro da pesquisa",
                    example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
            @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim",
                    description = "Data/hora de criação final para filtro da pesquisa",
                    example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
    })
    @Operation(summary = "Pesquisa os pedidos")
    @PageableParameter
    PagedModel<PedidoResumoDTO> pesquisar(@Parameter(hidden = true) PedidoFilter filtro,
                                          @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Registra um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoDTO adicionar(
            @RequestBody(description = "Representação de um novo pedido", required = true)
            PedidoInput pedidoInput);

    @Parameters({
            @Parameter(description = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", in = ParameterIn.QUERY  , schema = @Schema(type = "string"))
    })
    @Operation(summary = "Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"
                    , content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Problem.class)))
    })
    PedidoDTO buscar(
            @Parameter(description = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
            String codigoPedido);
}