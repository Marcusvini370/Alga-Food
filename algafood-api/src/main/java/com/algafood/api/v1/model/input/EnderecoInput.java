package com.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @Schema(example = "38400-000", required = true)
    @NotBlank
    private String cep;

    @Schema(example = "Rua Floriano Peixoto", required = true)
    @NotBlank
    private String logradouro;

    @Schema(example = "\"1500\"", required = true)
    @NotBlank
    private String numero;


    @Schema(example = "Apto 901")
    private String complemento;


    @Schema(example = "Centro", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}
