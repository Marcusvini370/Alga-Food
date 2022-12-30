package com.algafood.api.v1.openapi.controller;

import com.algafood.api.v1.controller.EstatisticasController;
import com.algafood.domain.filter.VendaDiariaFilter;
import com.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Estatísticas")
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    @Operation(summary = "Consulta estatísticas de vendas diárias")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "restauranteId", description = "ID do restaurante", example = "1", schema = @Schema(type = "integer")),
            @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio", description = "Data/hora inicial da criação do pedido", example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
            @Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim", description = "Data/hora final da criação do pedido", example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
    })
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
            @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")),
    })
    List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro,

            @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                    ref = "+00:00")
            String timeOffset);

    @Operation(summary = "Consulta estatísticas de vendas diárias com PDF")
    @Parameters({
            @Parameter(name = "restauranteId", description = "ID do restaurante",
                    example = "1", schema = @Schema(type = "integer")),
            @Parameter(name = "dataCriacaoInicio", description = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
            @Parameter(name = "dataCriacaoFim", description = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
    })
    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                    ref = "+00:00")
            String timeOffset);

    @Operation(description = "Estatísticas", hidden = true)
    EstatisticasController.EstatisticasModel estatisticas();
}