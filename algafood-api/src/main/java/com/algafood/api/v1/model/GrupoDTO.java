package com.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class GrupoDTO extends RepresentationModel<GrupoDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Gerente")
    private String nome;

}
