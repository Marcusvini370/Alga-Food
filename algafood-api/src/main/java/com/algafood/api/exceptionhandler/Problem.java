package com.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;


@JsonInclude(Include.NON_NULL)
@Getter
@Builder
@Schema(name = "Problema")
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "2022-02-14T16:39:13Z")
    private OffsetDateTime timestamp;

    @Schema(example = "https://algafood.com.br/recurso-nao-encontrado")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(example = "Objetos ou campos que geraram o erro")
    private List<Object> objects;


    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "preco")
        private String name;

        @Schema(example = "Opreço é obrigatório")
        private String userMessage;
    }
}
