package com.algafood.domain.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class GrupoInput {

    @NotBlank
    private String nome;

}  