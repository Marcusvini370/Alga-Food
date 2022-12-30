package com.algafood.api.v1.model.input;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CozinhaIdinput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long id;

}
