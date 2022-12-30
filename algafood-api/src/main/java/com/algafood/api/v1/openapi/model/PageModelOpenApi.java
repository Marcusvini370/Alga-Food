package com.algafood.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageModelOpenApi {

	@Schema(example = "10", description = "Quantidade de registros por página")
	private Long size;

	@Schema(example = "50", description = "Total de registros")
	private Long totalElements;

	@Schema(example = "5", description = "Total de páginas")
	private Long totalPages;

	@Schema(example = "0", description = "Número da página (começa em 0)")
	private Long number;

}