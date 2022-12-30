package com.algafood.domain.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.OffsetDateTime;

@Setter
@Getter
public class PedidoFilter {

    @Schema(example = "1", description = "ID do cliente para filtro da pesquisa")
    private Long clienteId;

    @Schema(example = "1", description = "ID do restaurante para filtro da pesquisa")
    private Long restauranteId;

    @Schema(example = "2019-10-30T00:00:00Z",
            description = "Data/hora de criação inicial para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @Schema(example = "2019-11-01T10:00:00Z",
            description = "Data/hora de criação final para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

}
